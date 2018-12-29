---
title: Android锁屏Activity实现
date: 2018-12-20 14:55:53
tags: [Android,锁屏]
categories: Android

---
之前公司要求在现有的APP上添加一个锁屏功能，一开始听着觉得好像还蛮简单的，真正写的时候写的自己一头包，因为在实现锁屏功能的过程中遇到了很多坑，在此记录下来。

整体逻辑
![](https://ws1.sinaimg.cn/large/6bbf23f6gy1fydau1sh98j21120l8gmq.jpg)

# 难点一 锁屏界面实现方式的选择 #

现在大部分锁屏由两种方式实现，第一种是使用悬浮窗的方式，实现一个全屏悬浮窗，来拦截所有点击事件实现的锁屏，另一种就是创建一个Activity，来假装自己是一个锁屏界面。  
## 悬浮窗的实现方式 ##

悬浮窗能够无视Home键，在按下home键的时候不会退到后台。所以不需要在home键的问题上纠结。悬浮窗统一由WindowManager来管理，具体的实现比较简单，笔者就不赘述了，有个坑要注意，悬浮窗需要声明权限：  

`<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />`  

有的手机设置里，默认是不给应用授权悬浮窗使用权的，所以应用里还要考虑引导用户授权悬浮窗使用。  
此外，有些应急解锁的场景，比如来电接听，闹铃处理，对于Activity实现的锁屏界面，系统会自动把所有的前台Activity隐藏，让用户直接去处理这些场景。但是悬浮窗会盖住场景，所以遇到这些场景，悬浮窗实现的锁屏界面要自己去处理这些特殊场景的自动解锁。


## Activity的实现方式 ##
也不知道为啥，我在想设计思路的时候下载的所有锁屏界面的实现方式都是使用一个假Activity的方式来充当锁屏界面的，国内基本所有的锁屏都是以此方式实现。

估计是因为悬浮窗的方式可能遇到的坑太多了，不敢保证自己能够全部规避，比如各种来电，各种消息提醒，还有各种第三方提醒，都可能和悬浮窗锁屏冲突，所以为了方便，反而实现Activity的方式会好些。所以我选择的是Activity的方式来实现的。

不过使用Activity的方式来实现的话，就不得不去屏蔽一下Home键，Back键和Menu键这三个可能有实体的按键了。

# 难点二  屏蔽Home键 #

既然是锁屏界面，当然只能通过界面上的一些滑动或者输入动作来解开锁屏，不能简单的直接被Home键一按，就解开了。从4.0开始，Home直接在framework层就被系统响应到，强退到桌面，第三方应用里已经无法再通过Activity.onKeyDown方法来监听和拦截Home键，尽管还象征性的保留了Home键的KeyCode来向前兼容，但是Home键按下去，并不会回调这个方法。

除了onKeyDown，有没有其他办法监听Home键，有的。前台App退到后台会有广播ACTION_CLOSE_SYSTEM_DIALOGS，收到广播携带的intent之后，解析里面的"reason"参数，就可以知道退出原因是什么了。home键按下后，reason是"homekey"，最近任务键按下后，reason是"recentapps"。

这当然不是最终方案，因为有些三星ROM里并不会有这个广播。而且广播的意思只是通知你一下，人家framework层已经把你的应用退回桌面了，你能监听home键，但没有办法拦截home键。也许想到了可以监听到home键的时候，马上把自己的Activity又重新打开展示，我试了一下，home键按下后startActivity会有延时3秒左右，这应该是Google早就想到了我们会这么干，做了这么一个延时方案。

直接拦截行不通了，想想别的路子。按Home键是让系统退回到Launcher（即桌面启动器），那么如果我们的锁屏Activity本身就是Launcher的话，那按Home键不就等于回到我们的锁屏Activity，也就可以阻止它把锁屏Activity关掉了。

怎么把自己的Activity声明为Launcher，在Activity中添加intent-filter：  

```xml 
<intent-filter>
    <action android:name="android.intent.action.MAIN" />
    <category android:name="android.intent.category.HOME" />
    <category android:name="android.intent.category.DEFAULT" />
</intent-filter>
```

这样，新安装的app会是一个能够作为launcher的app，所以首次按Home键的时候，就会有弹窗提示你选择要进入哪个launcher，选择我们自己的Activity，这样home键就被我们接管了。

不过这样有一个很明显的问题，如果不在我们的锁屏界面按Home键，同样会进入到锁屏Activity。当然，解决的方式也简单，当我们按Home时进入锁屏Activity的onCreate里做一个判断，如果前一个前台Activity是锁屏Activity，那就不用对Home键处理，如果不是锁屏Activity，那就要关闭锁屏Activity，跳到用户真正的桌面启动器去了。真正的桌面启动器是哪一个，我们可以这样来找：

```java  
List<String> pkgNamesT = new ArrayList<String>();
List<String> actNamesT = new ArrayList<String>();
List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);　　
for (int i = 0; i < resolveInfos.size(); i++) {
    String string = resolveInfos.get(i).activityInfo.packageName;
    if (!string.equals(context.getPackageName())) {//自己的launcher不要 　　　　　
        pkgNamesT.add(string);
        string = resolveInfos.get(i).activityInfo.name;
        actNamesT.add(string);
    }
}
```

如果实际的launcher只有一个，那直接跳转过去就可以了：

```java  
ComponentName componentName = new ComponentName(pkgName, actName); 
Intent intent = new Intent(); 
intent.setComponent(componentName); 
context.startActivity(intent); 
((Activity) context).finish();
```

如果手机安装有多个launcher（如360桌面一类的app）就会麻烦一点，需要展示一个列表让用户来选取用哪个launcher，这个在产品形态上可能会让用户觉得有点不解。
现在，如果在其他APP里按一下Home键，会跳到我们的锁屏Activity然后跳转到真正的launcher。这里可能会有Activity闪现一下的场景，影响用户体验。最优的办法其实是另外弄一个Activity来作为Home键跳转的Activity，这个Activity设为透明的，就不会被用户感知。如此，产品形态就变成了，锁屏Activity中按Home键，跳转到透明Activity，跳转回锁屏Activity，相当于Home键无效；其他APP中按Home键，跳转到透明Activity，跳转到真正的桌面。
实现透明的Activity，只需要在xml中声明。

```xml  
android:theme="@android:style/Theme.Translucent.NoTitleBar"
```  

这样的界面是透明的，实际上有占位在屏幕的顶层，所以跳转后记得要finish掉，不然会阻断跳转后的界面的交互。
另外，Theme.NoDisplay也能将Activity设置为不可见，而且不占位，但是笔者实现的时候发现，NoDisplay的Activity无法被系统设置为launcher（设置后会弹窗让你重新设置，如此反复）

## 难点三 何时加载锁屏Activity ##
参考国内应用，我选择在屏幕熄灭的时候来加载锁屏Activity，这样的话，如果想要在锁屏上加载东西可以有更多的缓冲时间，如果在亮屏是才监听则会出现闪一下锁屏前界面再出现我们的额锁屏界面的情况，用户体验很差。

说到加载锁屏Activity的话，就不得不先授予两个权限。

```xml  
<uses-permission android:name="android.permission.DISABLE_KEYGUARD" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
```  

只有应用被授予这两个权限后，才能在替换系统锁屏，并在后台唤起锁屏Activity。

有了自己的锁屏界面，还需要禁用掉系统的锁屏，以免造成用户需要解锁两次的局面。

首先我们需要知道用户是否设置了锁屏，方法如下：

对于API Level 16及以上SDK，可以使用如下方法判断是否有锁：  

`((KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE)).isKeyguardSecure()`  

对API Level 15及以下SDK，可以使用反射来判断：  

    try {
        Class<?> clazz = Class.forName("com.android.internal.widget.LockPatternUtils");
        Constructor<?> constructor = clazz.getConstructor(Context.class);
        constructor.setAccessible(true);
        Object utils = constructor.newInstance(this);
        Method method = clazz.getMethod("isSecure");
        return (Boolean) method.invoke(utils);
    }catch (Exception e){
        e.printStackTrace();
    }

得知用户设置了系统锁屏，怎么关掉呢？有前人建议了这种方法:

    KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
    KeyguardManager.KeyguardLock keyguardLock = km.newKeyguardLock("");
    keyguardLock.disableKeyguard();

这一步如果没有上面授的权的话，会无效。不过这种方法只能禁用滑动锁，如果用户设置的是图案或者PIN的锁的话，是无法直接取消的。禁用掉密码锁或者图案锁是一个很危险的行为，基于此，Google应该是不会把它开放给开发者的。

如果你想要自己锁屏界面的背景和桌面相同，可以简单设置：  

    <style name="LockScreenBase" parent="AppBaseTheme">
        <item name="android:windowIsTranslucent">true</item> 
        <item name="android:windowBackground">@android:color/transparent</item> 
        <item name="android:colorBackgroundCacheHint">@null</item> 
        <item name="android:windowNoTitle">true</item> 
        <item name="android:backgroundDimEnabled">false</item> 
        <item name="android:windowAnimationStyle">@null</item> 
        <item name="android:windowContentOverlay">@null</item> 
    </style>

将windowBackground属性设置为透明即可。

# 难点四 隐藏踪迹与独立的Task #

作为一个锁屏界面, 应当是独立的, 也就是说, 我们这个Activity应当独立于我们的App存在, 至少看起来是这样. 从Android的角度来看, 我们app的主界面里的所有Activity, 应当在一个Task里, 而锁屏Activity, 应当在一个独立的Task里, 因此我们需要给锁屏Activity一个独立的Task, 而且无论何时, 都只有一个锁屏Activity实例存在。   
另外, Android有查看近期任务的功能, 我们不希望锁屏界面这个独立的Task显示在里面, 所以锁屏Activity不能显示在近期任务中。  
说了这么多, 要做很简单, 只需要在Manifest里面声明Activity时加入几个属性即可  

```xml  
<activity 
android:excludeFromRecents="true" 
android:exported="false" 
android:launchMode="singleInstance"
android:name=".view.lockscreen.LockScreenActivity"
android:screenOrientation="portrait"
android:theme="@android:style/Theme.Wallpaper.NoTitleBar" />
```  

或者也可以这样写

```xml  
<activity 
android:excludeFromRecents="true" 
android:exported="false" 
android:launchMode="singleTask"
android:taskAffinity="com.xxx.xxx.view.lockscreen.LockScreenActivity"
android:name=".view.lockscreen.LockScreenActivity"
android:screenOrientation="portrait"
android:theme="@android:style/Theme.Wallpaper.NoTitleBar" />
```  

上面的属性中`android:excludeFromRecents="true"`让锁屏Activity不显示在近期任务中,`android:launchMode="singleInstance"`或者`android:launchMode="singleTask"`与`android:taskAffinity="com.package.name.lockscreen"`的组合保证锁屏Activity有一个单独的Task, 且这个Task里永远只有它一个实例。  
注意`android:taskAffinity`属性只能在启动模式是singleTask时生效。

# 难点五 处理黑色闪屏 #

我们的锁屏Activity在滑动”解锁”之后, 理论上是直接进入下面的界面, 但有时如果下面不是launcher, 而是一个app, 有可能会闪一下黑屏, 这个其实是底下activity的入场动画导致的, 某些Android版本会对顶部activity透明时处理有些奇怪, 我们不能保证其他的应用不闪黑屏, 但是对自己的的应用还是可以的, 只需要在我们的主体activity的style中加上

    <item name="android:windowAnimationStyle">@null</item>

就不会有这种情况发生了, 但是这样的话入场动画也没了, 总之如何取舍具体看需求吧。

基本上难点就以上说的那些，其实都是一些小细节，但是如果不一一处理的话，锁屏看起来会显得很假。然后下面我们就来继续说一下如何让锁屏看起来显得更自然，更逼真的方式。

# 沉浸式 #
什么是沉浸模式？从4.4开始，Android 为`setSystemUiVisibility()`方法提供了新的标记 `"SYSTEM_UI_FLAG_IMMERSIVE"`以及`"SYSTEM_UI_FLAG_IMMERSIVE_STIKY"`，就是我们所谈的沉浸模式，全称为 `"Immersive Full-Screen Mode"`，它可以使你的app隐藏状态栏和导航栏，实现真正意义上的全屏体验。

之前 Android 也是有全屏模式的，主要通过`setSystemUiVisibility()`添加两个Flag，即`"SYSTEM_UI_FLAG_FULLSCREEN"`，`"SYSTEM_UI_FLAG_HIDE_NAVIGATION"`（仅适用于使用导航栏的设备，即虚拟按键）。

这两个标记都存在一些问题，例如使用第一个标记的时候，除非 App 提供暂时退出全屏模式的功能（例如部分电子书软件中点击一次屏幕中央位置），用户是一直都没法看见状态栏的。这样，如果用户想去看看通知中心有什么通知，那就必须点击一次屏幕，显示状态栏，然后才能调出通知中心。

而第二个标记的问题在于，Google 认为导航栏对于用户来说是十分重要的，所以只会短暂隐藏导航栏。一旦用户做其他操作，例如点击一次屏幕，导航栏就会马上被重新调出。这样的设定对于看图软件，视频软件等等没什么大问题，但是对于游戏之类用户需要经常点击屏幕的 App，那就几乎是悲剧了——这也是为什么你在 Android 4.4 之前找不到什么全屏模式会自动隐藏导航栏的应用。

Android 4.4 之后加入的Immersive Full-Screen Mode 允许用户在应用全屏的情况下，通过在原有的状态栏/导航栏区域内做向内滑动的手势来实现短暂调出状态栏和导航栏的操作，且不会影响应用的正常全屏，短暂调出的状态栏和导航栏会呈半透明状态，并且在一段时间内或者用户与应用内元素进行互动的情况下自动隐藏，沉浸模式的四种状态如下图。

![](https://ws1.sinaimg.cn/large/6bbf23f6gy1fydb0ji2oxj20ix090q4o.jpg)

这里提供一个将状态栏设置为全透明的方法。  

    public static void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
             window.getDecorView().setSystemUiVisibility(uiOptions);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //隐藏虚拟键盘
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        } else if (Build.VERSION.SDK_INT < 19) {
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        }
    }

可以将状态栏设置完全透明，并且在有虚拟按键的情况下也可以隐藏虚拟按键。


# Service保活 #
作为一个锁屏APP，首先你得尽量做到不被用户一不小心杀死，才能够更多次的出现，所以要做到锁屏服务的保活也是十分重要的。关于Service的保活，我写了一篇单独的博客，可以在我的网页里检索一下。

# 版本适配机型适配 #
因为各个手机的ROM不同，系统版本不同，所以需要做一些特殊处理，比如说小米想要授权应用锁屏，是需要引导用户手动开启的，还有VIVO上也有同样的问题。对于华为手机，在6.0以上和6.0一下需要做不同的处理，这也是要注意的地方。
# 冲突处理 #
所谓冲突，则是如果一台手机存在多个锁屏APP，同时开启了锁屏界面的权限的话，会出现不同应用争抢同一个锁屏界面显示的情况，并且该情况还没有特别好的解决办法，暂时没法处理。等待后续迭代解决。