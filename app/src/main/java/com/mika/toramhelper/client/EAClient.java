package com.mika.toramhelper.client;

import com.mika.toramhelper.enchantment.bean.EFormula;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HTTP;

/**
 * @creater:moeta
 * @create time:2019/3/15 10:43
 * @description:
 */
public interface EAClient {
    @HTTP(method = "GET", path = "/EnchantmentFormula.json")
    Call<EFormula> repo();
}
