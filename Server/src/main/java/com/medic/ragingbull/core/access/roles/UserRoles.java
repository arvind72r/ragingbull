/*
 * Copyright (c) 2015
 *
 * RagingBull. All rights reserved
 */

package com.medic.ragingbull.core.access.roles;

/**
 * Created by Vamshi Molleti
 */

public class UserRoles{

    /**
     *
     // This is the privilege list for the platform. In total there are 64 privileges in total.
     // 16 privileges are allocated to each entity. Current entities are User, Practitioner, Pharmacy
     //      -------Unused--------       ------Pharmacist--------        ------Practitioner------        ---------User-----------
     //      11111111     11111111       11111111        11111111        11111111        11111111        11111111        11111111
     //                                                                                         131072   65536  512      256    2

     Values for User starting from 1
     2
     4
     8
     16
     32
     64
     128
     256
     512
     1024
     2048
     4096
     8192
     16384
     32768
     65536 --> End at 16

     Values for Practitioner starting from 17
     131072
     262144
     524288
     1048576
     2097152
     4194304
     8388608
     16777216
     33554432
     67108864
     134217728
     268435456
     536870912
     1073741824
     2147483648
     4294967296 --> End at 32

     Values for Pharmacist starting at 33
     8589934592
     17179869184
     34359738368
     68719476736
     137438953472
     274877906944
     549755813888
     1099511627776
     2199023255552
     4398046511104
     8796093022208
     17592186044416
     35184372088832
     70368744177664
     140737488355328
     281474976710656 --> End at 48

     Unused values starting at 49
     562949953421312
     1125899906842624
     2251799813685248
     4503599627370496
     9007199254740992
     18014398509481984
     36028797018963968
     72057594037927936
     144115188075855872
     288230376151711744
     576460752303423488
     1152921504606846976
     2305843009213693952
     4611686018427387904
     9223372036854775807 --> End at 64
     *
     * Created by Vamshi Molleti
     */
    public enum Permissions {

        // Block User
        BLOCK(0),

        // User Privileges
        USER_READ(2),
        USER_MODIFY(4),
        USER_DELETE(8),

        USER_MEMBER_READ(16),
        USER_MEMBER_ADD(32),
        USER_MEMBER_MODIFY(64),
        USER_MEMBER_DELETE(128),

        USER_CONSULTATION_READ(256),
        USER_CONSULTATION_MODIFY(512),
        USER_CONSULTATION_DELETE(1024),

        USER_ORDER_CREATE(2048),
        USER_ORDER_CANCEL(4096),

        // Practitioner Privileges
        PRACTITIONER_READ(131072),
        PRACTITIONER_MODIFY(262144),
        PRACTITIONER_DELETE(524288),

        PRACTITIONER_LOCATION_ADD(1048576),
        PRACTITIONER_LOCATION_MODIFY(2097152),
        PRACTITIONER_LOCATION_DELETE(4194304),

        PRACTITIONER_LOCATION_USER_ADD(8388608),
        PRACTITIONER_LOCATION_USER_REMOVE(16777216),

        PRACTITIONER_LOCATION_CONSULTATION_ADD(33554432),
        PRACTITIONER_LOCATION_CONSULTATION_MODIFY(67108864),
        PRACTITIONER_LOCATION_CONSULTATION_DELETE(134217728),

        PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD(268435456),

        // Pharmacist Privileges
        PHARMACIST_READ(8589934592L),
        PHARMACIST_MODIFY(17179869184L),
        PHARMACIST_DELETE(34359738368L),

        PHARMACIST_LOCATION_ADD(68719476736L),
        PHARMACIST_LOCATION_MODIFY(137438953472L),
        PHARMACIST_LOCATION_DELETE(274877906944L),

        PHARMACIST_LOCATION_USER_ADD(549755813888L),
        PHARMACIST_LOCATION_USER_REMOVE(1099511627776L),

        PHARMACIST_ORDER_READ(2199023255552L),
        PHARMACIST_ORDER_APPROVE(4398046511104L),
        PHARMACIST_ORDER_CANCEL(8796093022208L),

        PHARMACIST_INVENTORY_READ(17592186044416L),
        PHARMACIST_INVENTORY_MODIFY(35184372088832L),
        PHARMACIST_INVENTORY_DELETE(70368744177664L);

        private long BIT_VALUE;

        Permissions(long BIT_VALUE) {
            this.BIT_VALUE = BIT_VALUE;
        }

        public long getBitValue() {
            return BIT_VALUE;
        }
    }

    /**
     * There is an hierarchy of roles for each entity. The roles are in tree structure by default.
     *
     *  Entities are User, Practitioner, Pharmacy
     *
     *  User -
     *          NativeUser
     *
     *  Practitioner -
     *          NativePractitioner (Self) - (NativeUser + PRACTITIONER_READ + PRACTITIONER_MODIFY +  PRACTITIONER_DELETE)
     *                             (Users) - (NativeUser + PRACTITIONER_READ + PRACTITIONER_MODIFY)
     *
     *          NativePractitionerLocation (Self) - NativePractitioner + PRACTITIONER_LOCATION_ADD + PRACTITIONER_LOCATION_MODIFY + PRACTITIONER_LOCATION_DELETE + PRACTITIONER_LOCATION_USER_ADD + PRACTITIONER_LOCATION_USER_REMOVE
     *                         (Users) - (NativeUser + ChoiceOf (PRACTITIONER_LOCATION_ADD + PRACTITIONER_LOCATION_MODIFY + PRACTITIONER_LOCATION_USER_ADD + PRACTITIONER_LOCATION_USER_REMOVE)
     *
     *          NativeConsultation (Self) - NativeLocation + PRACTITIONER_LOCATION_CONSULTATION_ADD + PRACTITIONER_LOCATION_CONSULTATION_MODIFY + PRACTITIONER_LOCATION_CONSULTATION_DELETE + PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD
     *                             (Users) -  (NativeUser + ChoiceOf (PRACTITIONER_LOCATION_CONSULTATION_ADD + PRACTITIONER_LOCATION_CONSULTATION_MODIFY + PRACTITIONER_LOCATION_CONSULTATION_DELETE + PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD)
     *
     *  Pharmacist -
     *          NativePharmacist (Self) - (NativeUser + PHARMACIST_READ + PHARMACIST_MODIFY + PHARMACIST_DELETE)
     (Users) - (NativeUser + PHARMACIST_READ + PHARMACIST_MODIFY)
     *
     *         NativePharmacistLocation (Self) - (NativePharmacist + PHARMACIST_LOCATION_ADD + PHARMACIST_LOCATION_MODIFY + PHARMACIST_LOCATION_DELETE + PHARMACIST_ORDER_READ + PHARMACIST_ORDER_APPROVE + PHARMACIST_ORDER_CANCEL + PHARMACIST_INVENTORY_READ + PHARMACIST_INVENTORY_MODIFY + PHARMACIST_INVENTORY_DELETE)
     *                                  (Users) - (NativeUser + ChoiceOf (PHARMACIST_LOCATION_ADD + PHARMACIST_LOCATION_MODIFY + PHARMACIST_LOCATION_USER_ADD + PHARMACIST_LOCATION_USER_REMOVE + PHARMACIST_ORDER_APPROVE + PHARMACIST_ORDER_CANCEL + PHARMACIST_INVENTORY_READ + PHARMACIST_INVENTORY_MODIFY + PHARMACIST_INVENTORY_DELETE)
     *
     * Created by Vamshi Molleti
     */
    public enum Role {

        NATIVE_USER(Permissions.USER_READ, Permissions.USER_MODIFY, Permissions.USER_DELETE, Permissions.USER_MEMBER_ADD, Permissions.USER_MEMBER_READ,Permissions.USER_MEMBER_DELETE, Permissions.USER_MEMBER_MODIFY, Permissions.PRACTITIONER_LOCATION_CONSULTATION_ADD),
        NATIVE_MEMBER(Permissions.USER_READ, Permissions.USER_MODIFY, Permissions.USER_DELETE),
        NATIVE_PRACTITIONER(Permissions.USER_READ, Permissions.USER_MEMBER_ADD, Permissions.USER_DELETE, Permissions.PRACTITIONER_LOCATION_ADD , Permissions.PRACTITIONER_LOCATION_MODIFY , Permissions.PRACTITIONER_LOCATION_DELETE , Permissions.PRACTITIONER_LOCATION_USER_ADD , Permissions.PRACTITIONER_LOCATION_USER_REMOVE),
        NATIVE_PRACTITIONER_LOCATION(Permissions.USER_READ, Permissions.USER_MEMBER_ADD, Permissions.USER_DELETE, Permissions.PRACTITIONER_LOCATION_ADD , Permissions.PRACTITIONER_LOCATION_MODIFY , Permissions.PRACTITIONER_LOCATION_DELETE , Permissions.PRACTITIONER_LOCATION_USER_ADD , Permissions.PRACTITIONER_LOCATION_USER_REMOVE),
        NATIVE_CONSULTATION(Permissions.USER_READ, Permissions.USER_MEMBER_ADD, Permissions.USER_DELETE, Permissions.PRACTITIONER_LOCATION_ADD , Permissions.PRACTITIONER_LOCATION_MODIFY , Permissions.PRACTITIONER_LOCATION_DELETE , Permissions.PRACTITIONER_LOCATION_USER_ADD , Permissions.PRACTITIONER_LOCATION_USER_REMOVE, Permissions.PRACTITIONER_LOCATION_CONSULTATION_ADD , Permissions.PRACTITIONER_LOCATION_CONSULTATION_MODIFY , Permissions.PRACTITIONER_LOCATION_CONSULTATION_DELETE , Permissions.PRACTITIONER_LOCATION_CONSULTATION_PRESCRIPTION_ADD),
        NATIVE_PHARMACIST(Permissions.USER_READ, Permissions.USER_MEMBER_ADD, Permissions.USER_DELETE, Permissions.PHARMACIST_READ , Permissions.PHARMACIST_MODIFY , Permissions.PHARMACIST_DELETE),
        NATIVE_PHARMACIST_LOCATION(Permissions.USER_READ, Permissions.USER_MEMBER_ADD, Permissions.USER_DELETE, Permissions.PHARMACIST_READ , Permissions.PHARMACIST_MODIFY , Permissions.PHARMACIST_DELETE, Permissions.PHARMACIST_LOCATION_ADD , Permissions.PHARMACIST_LOCATION_MODIFY , Permissions.PHARMACIST_LOCATION_DELETE , Permissions.PHARMACIST_ORDER_READ , Permissions.PHARMACIST_ORDER_APPROVE , Permissions.PHARMACIST_ORDER_CANCEL , Permissions.PHARMACIST_INVENTORY_READ , Permissions.PHARMACIST_INVENTORY_MODIFY , Permissions.PHARMACIST_INVENTORY_DELETE);

        long ROLE_BIT = 0l;
        Role(Permissions... permissions) {
            for (Permissions permission : permissions) {
                ROLE_BIT = ROLE_BIT | permission.getBitValue();
            }
        }

        public long getRoleBit() {
            return ROLE_BIT;
        }
    }
}