/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.CartItem;
import com.medic.ragingbull.jdbi.mapper.CartMapper;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
@RegisterMapper(CartMapper.class)
public interface CartDao {
    @SqlBatch
    int[] addItem(String userId, List<CartItem> items);
}
