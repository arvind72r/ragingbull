/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.jdbi.dao;

import com.medic.ragingbull.api.Drug;

import java.util.List;

/**
 * Created by Vamshi Molleti
 */
public interface DrugsDao {
    int createAll(List<Drug> drugs);
}
