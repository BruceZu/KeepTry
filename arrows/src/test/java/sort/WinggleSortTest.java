//  Copyright 2016 The Sawdust Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package sort;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class WinggleSortTest extends TestCase {
    @Parameterized.Parameters(name = "test with {index} {0}")
    public static Iterable<Object[]> data() {
        Comparable[] expected = new Comparable[]{2499, 4999, 812, 4998, 562, 4517, 1187, 3780, 1437, 4991, 1687, 4989, 1937, 4988, 437, 4917, 2312, 3770, 962, 4981, 912, 4979, 862, 4978, 162, 3835, 762, 3825, 712, 4971, 662, 4969, 612, 4968, 112, 3810, 512, 3795, 1037, 4961, 1087, 4959, 1137, 4958, 237, 3790, 1237, 3830, 1287, 4951, 1337, 4949, 1387, 4948, 287, 4522, 1487, 3845, 1537, 4941, 1587, 4939, 1637, 4938, 337, 3757, 1737, 3925, 1787, 4931, 1837, 4929, 1887, 4928, 387, 3895, 1987, 3910, 2037, 4921, 2087, 4919, 2137, 4918, 87, 3860, 2237, 3920, 2287, 4911, 2337, 4909, 2387, 4908, 487, 3865, 2462, 3880, 992, 4901, 982, 4899, 972, 4898, 192, 4532, 952, 3930, 942, 4891, 932, 4889, 922, 4888, 182, 4005, 902, 3990, 892, 4881, 882, 4879, 872, 4878, 172, 3945, 852, 4010, 842, 4871, 832, 4869, 822, 4868, 32, 4085, 802, 3975, 792, 4861, 782, 4859, 772, 4858, 152, 4000, 752, 4020, 742, 4851, 732, 4849, 722, 4848, 142, 4302, 702, 4055, 692, 4841, 682, 4839, 672, 4838, 132, 4050, 652, 3950, 642, 4831, 632, 4829, 622, 4828, 122, 4070, 602, 4075, 592, 4821, 582, 4819, 572, 4818, 22, 3985, 552, 4045, 542, 4811, 532, 4809, 522, 4808, 102, 4090, 502, 4015, 1007, 4801, 1017, 4799, 1027, 4798, 207, 4802, 1047, 4125, 1057, 4791, 1067, 4789, 1077, 4788, 217, 4235, 1097, 4150, 1107, 4781, 1117, 4779, 1127, 4778, 227, 4115, 1147, 4165, 1157, 4771, 1167, 4769, 1177, 4768, 47, 4175, 1197, 4185, 1207, 4761, 1217, 4759, 1227, 4758, 247, 4200, 1247, 4260, 1257, 4751, 1267, 4749, 1277, 4748, 257, 4567, 1297, 4240, 1307, 4741, 1317, 4739, 1327, 4738, 267, 4922, 1347, 4160, 1357, 4731, 1367, 4729, 1377, 4728, 277, 4250, 1397, 4310, 1407, 4721, 1417, 4719, 1427, 4718, 57, 4255, 1447, 4285, 1457, 4711, 1467, 4709, 1477, 4708, 297, 4290, 1497, 4280, 1507, 4701, 1517, 4699, 1527, 4698, 307, 4572, 1547, 4300, 1557, 4691, 1567, 4689, 1577, 4688, 317, 4190, 1597, 4325, 1607, 4681, 1617, 4679, 1627, 4678, 327, 4145, 1647, 4315, 1657, 4671, 1667, 4669, 1677, 4668, 67, 4350, 1697, 4215, 1707, 4661, 1717, 4659, 1727, 4658, 347, 4355, 1747, 4360, 1757, 4651, 1767, 4649, 1777, 4648, 357, 4582, 1797, 4380, 1807, 4641, 1817, 4639, 1827, 4638, 367, 4400, 1847, 4405, 1857, 4631, 1867, 4629, 1877, 4628, 377, 4485, 1897, 4510, 1907, 4621, 1917, 4619, 1927, 4618, 77, 4370, 1947, 4440, 1957, 4611, 1967, 4609, 1977, 4608, 397, 4410, 1997, 4450, 2007, 4601, 2017, 4599, 2027, 4598, 407, 4527, 2047, 4470, 2057, 4591, 2067, 4589, 2077, 4588, 417, 4985, 2097, 4490, 2107, 4581, 2117, 4579, 2127, 4578, 427, 4500, 2147, 4610, 2157, 4571, 2167, 4569, 2177, 4568, 17, 4520, 2197, 4810, 2207, 4561, 2217, 4559, 2227, 4558, 447, 4540, 2247, 4545, 2257, 4551, 2267, 4549, 2277, 4548, 457, 4762, 2297, 4685, 2307, 4541, 2317, 4539, 2327, 4538, 467, 4710, 2347, 4575, 2357, 4531, 2367, 4529, 2377, 4528, 477, 4595, 2397, 4435, 2407, 4521, 2417, 4519, 2427, 4518, 97, 4960, 2447, 4615, 2457, 4511, 2467, 4509, 2477, 4508, 497, 4625, 2492, 4760, 998, 4501, 996, 4499, 994, 4498, 198, 4617, 990, 4560, 988, 4491, 986, 4489, 984, 4488, 196, 4932, 980, 4665, 978, 4481, 976, 4479, 974, 4478, 194, 4675, 970, 4680, 968, 4471, 966, 4469, 964, 4468, 38, 4695, 960, 4555, 958, 4461, 956, 4459, 954, 4458, 190, 4715, 950, 4720, 948, 4451, 946, 4449, 944, 4448, 188, 4622, 940, 4785, 938, 4441, 936, 4439, 934, 4438, 186, 4955, 930, 4750, 928, 4431, 926, 4429, 924, 4428, 184, 4770, 920, 4905, 918, 4421, 916, 4419, 914, 4418, 36, 4880, 910, 4790, 908, 4411, 906, 4409, 904, 4408, 180, 4800, 900, 4830, 898, 4401, 896, 4399, 894, 4398, 178, 4632, 890, 4580, 888, 4391, 886, 4389, 884, 4388, 176, 4840, 880, 4845, 878, 4381, 876, 4379, 874, 4378, 174, 4885, 870, 4865, 868, 4371, 866, 4369, 864, 4368, 34, 4730, 860, 4875, 858, 4361, 856, 4359, 854, 4358, 170, 4890, 850, 4895, 848, 4351, 846, 4349, 844, 4348, 168, 4477, 840, 4655, 838, 4341, 836, 4339, 834, 4338, 166, 4630, 830, 4925, 828, 4331, 826, 4329, 824, 4328, 164, 4945, 820, 4430, 818, 4321, 816, 4319, 814, 4318, 6, 4505, 810, 4965, 808, 4311, 806, 4309, 804, 4308, 160, 4415, 800, 4975, 798, 4301, 796, 4299, 794, 4298, 158, 4352, 790, 4995, 788, 4291, 786, 4289, 784, 4288, 156, 4390, 780, 4507, 778, 4281, 776, 4279, 774, 4278, 154, 4727, 770, 4907, 768, 4271, 766, 4269, 764, 4268, 30, 4482, 760, 4662, 758, 4261, 756, 4259, 754, 4258, 150, 3952, 750, 4467, 748, 4251, 746, 4249, 744, 4248, 148, 4667, 740, 4447, 738, 4241, 736, 4239, 734, 4238, 146, 3852, 730, 4442, 728, 4231, 726, 4229, 724, 4228, 144, 4422, 720, 4852, 718, 4221, 716, 4219, 714, 4218, 28, 4417, 710, 4407, 708, 4211, 706, 4209, 704, 4208, 140, 4397, 700, 4877, 698, 4201, 696, 4199, 694, 4198, 138, 4672, 690, 4382, 688, 4191, 686, 4189, 684, 4188, 136, 4952, 680, 4882, 678, 4181, 676, 4179, 674, 4178, 134, 4712, 670, 4347, 668, 4171, 666, 4169, 664, 4168, 26, 3877, 660, 4342, 658, 4161, 656, 4159, 654, 4158, 130, 4322, 650, 4577, 648, 4151, 646, 4149, 644, 4148, 128, 4682, 640, 4307, 638, 4141, 636, 4139, 634, 4138, 126, 4452, 630, 3977, 628, 4131, 626, 4129, 624, 4128, 124, 4537, 620, 4982, 618, 4121, 616, 4119, 614, 4118, 24, 4137, 610, 4867, 608, 4111, 606, 4109, 604, 4108, 120, 4257, 600, 4487, 598, 4101, 596, 4099, 594, 4098, 118, 4277, 590, 4857, 588, 4091, 586, 4089, 584, 4088, 116, 4262, 580, 4222, 578, 4081, 576, 4079, 574, 4078, 114, 4217, 570, 4207, 568, 4071, 566, 4069, 564, 4068, 4, 4197, 560, 4412, 558, 4061, 556, 4059, 554, 4058, 110, 4182, 550, 4512, 548, 4051, 546, 4049, 544, 4048, 108, 4152, 540, 4102, 538, 4041, 536, 4039, 534, 4038, 106, 4812, 530, 4587, 528, 4031, 526, 4029, 524, 4028, 104, 4842, 520, 4142, 518, 4021, 516, 4019, 514, 4018, 20, 4337, 510, 4122, 508, 4011, 506, 4009, 504, 4008, 100, 4117, 500, 4107, 1001, 4001, 1003, 3999, 1005, 3998, 201, 4717, 1009, 4937, 1011, 3991, 1013, 3989, 1015, 3988, 203, 3927, 1019, 4082, 1021, 3981, 1023, 3979, 1025, 3978, 205, 4562, 1029, 4822, 1031, 3971, 1033, 3969, 1035, 3968, 41, 4057, 1039, 3937, 1041, 3961, 1043, 3959, 1045, 3958, 209, 4052, 1049, 4042, 1051, 3951, 1053, 3949, 1055, 3948, 211, 4722, 1059, 4972, 1061, 3941, 1063, 3939, 1065, 3938, 213, 4817, 1069, 4017, 1071, 3931, 1073, 3929, 1075, 3928, 215, 3997, 1079, 4862, 1081, 3921, 1083, 3919, 1085, 3918, 43, 3992, 1089, 3982, 1091, 3911, 1093, 3909, 1095, 3908, 219, 4087, 1099, 4002, 1101, 3901, 1103, 3899, 1105, 3898, 221, 4732, 1109, 4037, 1111, 3891, 1113, 3889, 1115, 3888, 223, 4797, 1119, 3942, 1121, 3881, 1123, 3879, 1125, 3878, 225, 3922, 1129, 3812, 1131, 3871, 1133, 3869, 1135, 3868, 45, 3917, 1139, 3907, 1141, 3861, 1143, 3859, 1145, 3858, 229, 3802, 1149, 3897, 1151, 3851, 1153, 3849, 1155, 3848, 231, 4202, 1159, 3892, 1161, 3841, 1163, 3839, 1165, 3838, 233, 3872, 1169, 3912, 1171, 3831, 1173, 3829, 1175, 3828, 235, 3857, 1179, 4787, 1181, 3821, 1183, 3819, 1185, 3818, 9, 3887, 1189, 4177, 1191, 3811, 1193, 3809, 1195, 3808, 239, 3862, 1199, 3782, 1201, 3801, 1203, 3799, 1205, 3798, 241, 4027, 1209, 3817, 1211, 3791, 1213, 3789, 1215, 3788, 243, 3797, 1219, 3762, 1221, 3781, 1223, 3779, 1225, 3778, 245, 3792, 1229, 4967, 1231, 3771, 1233, 3769, 1235, 3768, 49, 3772, 1239, 3747, 1241, 3761, 1243, 3759, 1245, 4187, 249, 3748, 1249, 3754, 1251, 3753, 1253, 3750, 1255, 3746, 251, 3745, 1259, 3263, 1261, 3743, 1263, 3255, 1265, 3739, 253, 3711, 1269, 3266, 1271, 3734, 1273, 3730, 1275, 3729, 255, 3291, 1279, 3268, 1281, 3724, 1283, 3720, 1285, 3719, 51, 3721, 1289, 3298, 1291, 3713, 1293, 3707, 1295, 3709, 259, 3706, 1299, 3336, 1301, 3703, 1303, 3307, 1305, 3722, 261, 3701, 1309, 3348, 1311, 3694, 1313, 3690, 1315, 3717, 263, 3561, 1319, 3303, 1321, 3683, 1323, 3680, 1325, 3679, 265, 3311, 1329, 3305, 1331, 3673, 1333, 3277, 1335, 3669, 53, 3671, 1339, 3330, 1341, 3663, 1343, 3323, 1345, 3697, 269, 3656, 1349, 3453, 1351, 3654, 1353, 3650, 1355, 3649, 271, 3651, 1359, 3470, 1361, 3643, 1363, 3408, 1365, 3662, 273, 3443, 1369, 3441, 1371, 3633, 1373, 3413, 1375, 3629, 275, 3455, 1379, 3433, 1381, 3624, 1383, 3620, 1385, 3672, 55, 3621, 1389, 3415, 1391, 3613, 1393, 3327, 1395, 3609, 279, 3606, 1399, 3374, 1401, 3604, 1403, 3600, 1405, 3599, 281, 3601, 1409, 3424, 1411, 3594, 1413, 3590, 1415, 3642, 283, 3611, 1419, 3461, 1421, 3583, 1423, 3425, 1425, 3579, 285, 3391, 1429, 3416, 1431, 3573, 1433, 3392, 1435, 3569, 11, 3571, 1439, 3466, 1441, 3563, 1443, 3402, 1445, 3559, 289, 3556, 1449, 3688, 1451, 3554, 1453, 3550, 1455, 3622, 291, 3551, 1459, 3535, 1461, 3544, 1463, 3540, 1465, 3539, 293, 3536, 1469, 3725, 1471, 3534, 1473, 3530, 1475, 3529, 295, 3566, 1479, 3491, 1481, 3523, 1483, 3457, 1485, 3597, 59, 3521, 1489, 3503, 1491, 3514, 1493, 3510, 1495, 3509, 299, 3506, 1499, 3520, 1501, 3504, 1503, 3500, 1505, 3499, 301, 3501, 1509, 3541, 1511, 3493, 1513, 3543, 1515, 3492, 303, 3511, 1519, 3553, 1521, 3484, 1523, 3480, 1525, 3567, 305, 3565, 1529, 3586, 1531, 3473, 1533, 3432, 1535, 3469, 61, 3471, 1539, 3658, 1541, 3464, 1543, 3460, 1545, 3459, 309, 3456, 1549, 3595, 1551, 3454, 1553, 3450, 1555, 3547, 311, 3451, 1559, 3616, 1561, 3444, 1563, 3440, 1565, 3439, 313, 3623, 1569, 3630, 1571, 3434, 1573, 3430, 1575, 3429, 315, 3641, 1579, 3653, 1581, 3442, 1583, 3423, 1585, 3419, 63, 3421, 1589, 3665, 1591, 3414, 1593, 3410, 1595, 3517, 319, 3406, 1599, 3661, 1601, 3403, 1603, 3733, 1605, 3582, 321, 3401, 1609, 3693, 1611, 3393, 1613, 3287, 1615, 3389, 323, 3700, 1619, 3715, 1621, 3384, 1623, 3380, 1625, 3379, 325, 3505, 1629, 3555, 1631, 3497, 1633, 3373, 1635, 3369, 65, 3371, 1639, 3735, 1641, 3364, 1643, 3360, 1645, 3477, 329, 3356, 1649, 3740, 1651, 3353, 1653, 3257, 1655, 3617, 331, 3351, 1659, 3437, 1661, 3344, 1663, 3340, 1665, 3472, 333, 3361, 1669, 3387, 1671, 3333, 1673, 3272, 1675, 3482, 335, 3282, 1679, 3292, 1681, 3324, 1683, 3320, 1685, 3319, 13, 3321, 1689, 3302, 1691, 3314, 1693, 3310, 1695, 3309, 339, 3306, 1699, 3362, 1701, 3304, 1703, 3300, 1705, 3447, 341, 3301, 1709, 3342, 1711, 3294, 1713, 3290, 1715, 3289, 343, 3286, 1719, 3382, 1721, 3284, 1723, 3280, 1725, 3279, 345, 3487, 1729, 3377, 1731, 3274, 1733, 3270, 1735, 3422, 69, 3271, 1739, 3397, 1741, 3264, 1743, 3260, 1745, 3259, 349, 3256, 1749, 3244, 1751, 3254, 1753, 3250, 1755, 3527, 351, 3251, 1759, 2901, 1761, 2904, 1763, 2900, 1765, 3239, 353, 3233, 1769, 2910, 1771, 3231, 1773, 2909, 1775, 3229, 355, 2934, 1779, 2972, 1781, 3224, 1783, 2948, 1785, 3221, 71, 2942, 1789, 2931, 1791, 3214, 1793, 2971, 1795, 3209, 359, 3067, 1799, 2984, 1801, 3203, 1803, 3200, 1805, 3201, 361, 3015, 1809, 3025, 1811, 3195, 1813, 3193, 1815, 3189, 363, 3043, 1819, 2941, 1821, 3183, 1823, 3188, 1825, 3179, 365, 3074, 1829, 3081, 1831, 3176, 1833, 3170, 1835, 3171, 73, 3090, 1839, 3063, 1841, 3161, 1843, 3213, 1845, 3159, 369, 3110, 1849, 3160, 1851, 3153, 1853, 3130, 1855, 3151, 371, 3145, 1859, 3155, 1861, 3144, 1863, 3168, 1865, 3139, 373, 2936, 1869, 3178, 1871, 3131, 1873, 3181, 1875, 3129, 375, 3035, 1879, 3005, 1881, 3123, 1883, 3215, 1885, 3121, 75, 3238, 1889, 2956, 1891, 3114, 1893, 2981, 1895, 3109, 379, 3108, 1899, 2906, 1901, 3103, 1903, 3199, 1905, 3101, 381, 3100, 1909, 3216, 1911, 3094, 1913, 3165, 1915, 3111, 383, 3191, 1919, 3180, 1921, 3083, 1923, 3211, 1925, 3079, 385, 3080, 1929, 3135, 1931, 3076, 1933, 3070, 1935, 3222, 15, 3078, 1939, 3166, 1941, 3064, 1943, 3124, 1945, 3059, 389, 3068, 1949, 3141, 1951, 3050, 1953, 2998, 1955, 3051, 391, 3053, 1959, 2995, 1961, 3044, 1963, 2990, 1965, 3039, 393, 3038, 1969, 2996, 1971, 3033, 1973, 2985, 1975, 3227, 395, 3030, 1979, 2979, 1981, 3024, 1983, 2980, 1985, 3021, 79, 3023, 1989, 2974, 1991, 3013, 1993, 2916, 1995, 3009, 399, 3016, 1999, 2968, 2001, 3003, 2003, 2969, 2005, 3217, 401, 3122, 2009, 2963, 2011, 2987, 2013, 2959, 2015, 3197, 403, 3077, 2019, 2958, 2021, 3032, 2023, 2954, 2025, 3172, 405, 2950, 2029, 2949, 2031, 3117, 2033, 2986, 2035, 3152, 81, 3082, 2039, 2966, 2041, 3132, 2043, 2926, 2045, 3207, 409, 2907, 2049, 2940, 2051, 3097, 2053, 2917, 2055, 3177, 411, 3127, 2059, 2930, 2061, 3072, 2063, 2933, 2065, 3167, 413, 2961, 2069, 2929, 2071, 3042, 2073, 2925, 2075, 3162, 415, 2924, 2079, 2920, 2081, 2952, 2083, 3017, 2085, 2982, 83, 2947, 2089, 2962, 2091, 2937, 2093, 2903, 2095, 3212, 419, 2957, 2099, 3057, 2101, 2913, 2103, 2715, 2105, 2876, 421, 2734, 2109, 2712, 2111, 2846, 2113, 2724, 2115, 2898, 423, 2738, 2119, 2749, 2121, 2880, 2123, 2856, 2125, 2837, 425, 2736, 2129, 2730, 2131, 2888, 2133, 2806, 2135, 2887, 85, 2895, 2139, 2821, 2141, 2867, 2143, 2802, 2145, 2882, 429, 2886, 2149, 2791, 2151, 2737, 2153, 2772, 2155, 2874, 431, 2892, 2159, 2838, 2161, 2847, 2163, 2727, 2165, 2868, 433, 2824, 2169, 2817, 2171, 2722, 2173, 2884, 2175, 2866, 435, 2875, 2179, 2826, 2181, 2859, 2183, 2777, 2185, 2864, 3, 2877, 2189, 2889, 2191, 2762, 2193, 2816, 2195, 2776, 439, 2869, 2199, 2873, 2201, 2823, 2203, 2758, 2205, 2818, 441, 2879, 2209, 2735, 2211, 2743, 2213, 2829, 2215, 2853, 443, 2815, 2219, 2716, 2221, 2746, 2223, 2753, 2225, 2745, 445, 2731, 2229, 2839, 2231, 2719, 2233, 2760, 2235, 2728, 89, 2827, 2239, 2764, 2241, 2765, 2243, 2726, 2245, 2850, 449, 2797, 2249, 2714, 2251, 2718, 2253, 2828, 2255, 2805, 451, 2854, 2259, 2774, 2261, 2803, 2263, 2775, 2265, 2778, 453, 2711, 2269, 2780, 2271, 2799, 2273, 2842, 2275, 2798, 455, 2792, 2279, 2783, 2281, 2811, 2283, 2784, 2285, 2845, 91, 2740, 2289, 2733, 2291, 2789, 2293, 2708, 2295, 2696, 459, 2607, 2299, 2608, 2301, 2688, 2303, 2617, 2305, 2698, 461, 2707, 2309, 2639, 2311, 2679, 2313, 2637, 2315, 2664, 463, 2612, 2319, 2619, 2321, 2675, 2323, 2615, 2325, 2663, 465, 2694, 2329, 2666, 2331, 2610, 2333, 2676, 2335, 2609, 93, 2690, 2339, 2650, 2341, 2682, 2343, 2648, 2345, 2704, 469, 2669, 2349, 2702, 2351, 2627, 2353, 2657, 2355, 2686, 471, 2683, 2359, 2642, 2361, 2623, 2363, 2677, 2365, 2651, 473, 2640, 2369, 2659, 2371, 2628, 2373, 2645, 2375, 2652, 475, 2653, 2379, 2670, 2381, 2662, 2383, 2629, 2385, 2700, 95, 2641, 2389, 2611, 2391, 2634, 2393, 2654, 2395, 2638, 479, 2556, 2399, 2576, 2401, 2548, 2403, 2552, 2405, 2597, 481, 2603, 2409, 2575, 2411, 2602, 2413, 2565, 2415, 2591, 483, 2567, 2419, 2549, 2421, 2560, 2423, 2569, 2425, 2601, 485, 2588, 2429, 2566, 2431, 2581, 2433, 2557, 2435, 2573, 19, 2584, 2439, 2571, 2441, 2577, 2443, 2574, 2445, 2599, 489, 2555, 2449, 2580, 2451, 2594, 2453, 2586, 2455, 2538, 491, 2529, 2459, 2531, 2461, 2522, 2463, 2534, 2465, 2544, 493, 2523, 2469, 2525, 2471, 2539, 2473, 2526, 2475, 2536, 495, 2524, 2479, 2530, 2481, 2520, 2483, 2510, 2485, 2519, 99, 2514, 2489, 2511, 2491, 2512, 2493, 2508, 2495, 2504, 499, 2506, 2498, 2502, 1562, 2500, 937, 2507, 2497, 2503, 2496, 2505, 2494, 2509, 498, 2513, 2490, 2515, 2488, 2517, 2486, 2518, 2484, 2516, 496, 2545, 2480, 2546, 2478, 2535, 2476, 2542, 2474, 2528, 494, 2541, 2470, 2521, 2468, 2533, 2466, 2537, 2464, 2540, 98, 2527, 2460, 2543, 2458, 2532, 2456, 2547, 2454, 2583, 490, 2595, 2450, 2553, 2448, 2585, 2446, 2551, 2444, 2568, 488, 2598, 2440, 2572, 2438, 2570, 2436, 2554, 2434, 2589, 486, 2592, 2430, 2558, 2428, 2590, 2426, 2579, 2424, 2593, 484, 2587, 2420, 2596, 2418, 2564, 2416, 2563, 2414, 2600, 96, 2604, 2410, 2550, 2408, 2582, 2406, 2562, 2404, 2578, 480, 2559, 2400, 2561, 2398, 2605, 2396, 2621, 2394, 2667, 478, 2693, 2390, 2672, 2388, 2633, 2386, 2687, 2384, 2689, 476, 2668, 2380, 2643, 2378, 2636, 2376, 2681, 2374, 2624, 474, 2665, 2370, 2661, 2368, 2626, 2366, 2644, 2364, 2680, 94, 2660, 2360, 2647, 2358, 2620, 2356, 2697, 2354, 2655, 470, 2685, 2350, 2684, 2348, 2632, 2346, 2692, 2344, 2646, 468, 2656, 2340, 2674, 2338, 2673, 2336, 2616, 2334, 2691, 466, 2671, 2330, 2613, 2328, 2635, 2326, 2622, 2324, 2699, 464, 2678, 2320, 2703, 2318, 2618, 2316, 2649, 2314, 2705, 18, 2706, 2310, 2630, 2308, 2625, 2306, 2658, 2304, 2631, 460, 2701, 2300, 2614, 2298, 2695, 2296, 2606, 2294, 2788, 458, 2790, 2290, 2785, 2288, 2793, 2286, 2732, 2284, 2754, 456, 2848, 2280, 2855, 2278, 2795, 2276, 2844, 2274, 2819, 454, 2800, 2270, 2779, 2268, 2810, 2266, 2748, 2264, 2834, 90, 2804, 2260, 2739, 2258, 2843, 2256, 2773, 2254, 2752, 450, 2809, 2250, 2770, 2248, 2769, 2246, 2768, 2244, 2725, 448, 2849, 2240, 2763, 2238, 2761, 2236, 2840, 2234, 2858, 446, 2891, 2230, 2709, 2228, 2835, 2226, 2814, 2224, 2787, 444, 2744, 2220, 2833, 2218, 2812, 2216, 2830, 2214, 2860, 88, 2750, 2210, 2710, 2208, 2825, 2206, 2759, 2204, 2865, 440, 2796, 2200, 2872, 2198, 2757, 2196, 2794, 2194, 2851, 438, 2771, 2190, 2717, 2188, 2807, 2186, 2857, 2184, 2878, 436, 2863, 2180, 2786, 2178, 2756, 2176, 2766, 2174, 2881, 434, 2767, 2170, 2801, 2168, 2862, 2166, 2782, 2164, 2885, 86, 2870, 2160, 2747, 2158, 2861, 2156, 2836, 2154, 2820, 430, 2841, 2150, 2813, 2148, 2832, 2146, 2781, 2144, 2897, 428, 2883, 2140, 2808, 2138, 2852, 2136, 2755, 2134, 2896, 426, 2890, 2130, 2741, 2128, 2822, 2126, 2742, 2124, 2729, 424, 2894, 2120, 2751, 2118, 2871, 2116, 2723, 2114, 2721, 84, 2893, 2110, 2720, 2108, 2831, 2106, 2713, 2104, 2899, 420, 3062, 2100, 3012, 2098, 3087, 2096, 3037, 2094, 3010, 418, 3112, 2090, 2922, 2088, 2967, 2086, 2914, 2084, 3007, 416, 2977, 2080, 2919, 2078, 3022, 2076, 2923, 2074, 2997, 414, 3047, 2070, 2928, 2068, 3061, 2066, 2991, 2064, 3086, 16, 3182, 2060, 3011, 2058, 3137, 2056, 2935, 2054, 2938, 410, 3107, 2050, 2939, 2048, 3102, 2046, 2943, 2044, 3066, 408, 3187, 2040, 2945, 2038, 3142, 2036, 2944, 2034, 3092, 406, 3147, 2030, 2953, 2028, 3157, 2026, 2955, 2024, 2912, 404, 3052, 2020, 2951, 2018, 3192, 2016, 2960, 2014, 3000, 80, 3002, 2010, 2965, 2008, 3202, 2006, 2964, 2004, 3008, 400, 3004, 2000, 2976, 1998, 3006, 1996, 2973, 1994, 2975, 398, 3014, 1990, 2978, 1988, 3019, 1986, 2911, 1984, 3028, 396, 3026, 1980, 2983, 1978, 3029, 1976, 3001, 1974, 3040, 394, 3034, 1970, 2989, 1968, 3036, 1966, 2993, 1964, 3048, 78, 3046, 1960, 2994, 1958, 3049, 1956, 2999, 1954, 3058, 390, 3054, 1950, 3133, 1948, 3056, 1946, 3163, 1944, 3060, 388, 3186, 1940, 3065, 1938, 3071, 1936, 3158, 1934, 3115, 386, 3232, 1930, 3148, 1928, 3075, 1926, 3143, 1924, 3091, 384, 3084, 1920, 2927, 1918, 3089, 1916, 3173, 1914, 3098, 76, 3096, 1910, 3208, 1908, 3099, 1906, 3088, 1904, 3190, 380, 3104, 1900, 3230, 1898, 3106, 1896, 3073, 1894, 3210, 378, 3136, 1890, 3118, 1888, 3120, 1886, 3220, 1884, 3241, 376, 3126, 1880, 3205, 1878, 3128, 1876, 3194, 1874, 3125, 374, 3134, 1870, 3085, 1868, 3138, 1866, 3174, 1864, 3105, 74, 3146, 1860, 2932, 1858, 3149, 1856, 3140, 1854, 3027, 370, 3154, 1850, 3119, 1848, 3156, 1846, 3116, 1844, 3218, 368, 3164, 1840, 3095, 1838, 3169, 1836, 3093, 1834, 3242, 366, 3237, 1830, 3150, 1828, 3175, 1826, 3069, 1824, 3055, 364, 3184, 1820, 3045, 1818, 3185, 1816, 3041, 1814, 3031, 14, 3196, 1810, 3020, 1808, 3198, 1806, 3018, 1804, 3223, 360, 3204, 1800, 2988, 1798, 3206, 1796, 2970, 1794, 3225, 358, 3236, 1790, 2946, 1788, 3219, 1786, 2921, 1784, 2992, 356, 3226, 1780, 3113, 1778, 3228, 1776, 2915, 1774, 2908, 354, 3234, 1770, 2918, 1768, 3235, 1766, 2905, 1764, 2902, 70, 3240, 1760, 3243, 1758, 3249, 1756, 3245, 1754, 3702, 350, 3407, 1750, 3246, 1748, 3258, 1746, 3412, 1744, 3261, 348, 3417, 1740, 3265, 1738, 3269, 1736, 3627, 1734, 3276, 346, 3677, 1730, 3312, 1728, 3278, 1726, 3372, 1724, 3281, 344, 3427, 1720, 3352, 1718, 3288, 1716, 3347, 1714, 3296, 68, 3657, 1710, 3295, 1708, 3299, 1706, 3357, 1704, 3262, 340, 3587, 1700, 3537, 1698, 3308, 1696, 3322, 1694, 3562, 338, 3667, 1690, 3337, 1688, 3318, 1686, 3297, 1684, 3326, 336, 3452, 1680, 3325, 1678, 3329, 1676, 3462, 1674, 3331, 334, 3334, 1670, 3335, 1668, 3339, 1666, 3252, 1664, 3346, 66, 3687, 1660, 3345, 1658, 3349, 1656, 3247, 1654, 3488, 330, 3354, 1650, 3355, 1648, 3359, 1646, 3580, 1644, 3386, 328, 3507, 1640, 3731, 1638, 3368, 1636, 3370, 1634, 3376, 326, 3612, 1630, 3723, 1628, 3378, 1626, 3716, 1624, 3381, 324, 3552, 1620, 3605, 1618, 3388, 1616, 3698, 1614, 3396, 64, 3394, 1610, 3395, 1608, 3399, 1606, 3682, 1604, 3670, 320, 3404, 1600, 3405, 1598, 3409, 1596, 3666, 1594, 3411, 318, 3522, 1590, 3533, 1588, 3418, 1586, 3420, 1584, 3426, 316, 3577, 1580, 3645, 1578, 3428, 1576, 3640, 1574, 3431, 314, 3532, 1570, 3625, 1568, 3438, 1566, 3618, 1564, 3446, 0, 3542, 1560, 3445, 1558, 3449, 1556, 3560, 1554, 3603, 310, 3502, 1550, 3593, 1548, 3458, 1546, 3591, 1544, 3486, 308, 3512, 1540, 3575, 1538, 3468, 1536, 3570, 1534, 3476, 306, 3474, 1530, 3475, 1528, 3479, 1526, 3710, 1524, 3481, 304, 3572, 1520, 3485, 1518, 3489, 1516, 3548, 1514, 3496, 60, 3494, 1510, 3467, 1508, 3498, 1506, 3660, 1504, 3525, 300, 3607, 1500, 3518, 1498, 3508, 1496, 3516, 1494, 3638, 298, 3592, 1490, 3515, 1488, 3519, 1486, 3495, 1484, 3526, 296, 3524, 1480, 3490, 1478, 3528, 1476, 3705, 1474, 3531, 294, 3602, 1470, 3675, 1468, 3538, 1466, 3588, 1464, 3546, 58, 3632, 1460, 3545, 1458, 3549, 1456, 3610, 1454, 3513, 290, 3637, 1450, 3691, 1448, 3558, 1446, 3483, 1444, 3436, 288, 3564, 1440, 3365, 1438, 3568, 1436, 3463, 1434, 3576, 286, 3574, 1430, 3367, 1428, 3578, 1426, 3400, 1424, 3581, 284, 3584, 1420, 3585, 1418, 3589, 1416, 3398, 1414, 3596, 56, 3647, 1410, 3332, 1408, 3598, 1406, 3390, 1404, 3375, 280, 3652, 1400, 3366, 1398, 3608, 1396, 3385, 1394, 3636, 278, 3614, 1390, 3615, 1388, 3619, 1386, 3435, 1384, 3626, 276, 3712, 1380, 3317, 1378, 3628, 1376, 3383, 1374, 3631, 274, 3634, 1370, 3635, 1368, 3639, 1366, 3465, 1364, 3646, 54, 3644, 1360, 3557, 1358, 3648, 1356, 3478, 1354, 3448, 270, 3692, 1350, 3655, 1348, 3659, 1346, 3363, 1344, 3341, 268, 3664, 1340, 3267, 1338, 3668, 1336, 3328, 1334, 3676, 266, 3674, 1330, 3313, 1328, 3678, 1326, 3315, 1324, 3681, 264, 3684, 1320, 3685, 1318, 3689, 1316, 3358, 1314, 3696, 10, 3737, 1310, 3695, 1308, 3699, 1306, 3350, 1304, 3338, 260, 3704, 1300, 3343, 1298, 3708, 1296, 3316, 1294, 3686, 258, 3714, 1290, 3275, 1288, 3718, 1286, 3285, 1284, 3726, 256, 3727, 1280, 3283, 1278, 3728, 1276, 3293, 1274, 3273, 254, 3742, 1270, 3732, 1268, 3738, 1266, 3253, 1264, 3736, 50, 3744, 1260, 3741, 1258, 3248, 1256, 3749, 1254, 3837, 250, 3767, 1250, 3755, 1248, 3758, 1246, 3752, 1244, 4767, 248, 3763, 1240, 3764, 1238, 3766, 1236, 3777, 1234, 4757, 246, 3773, 1230, 3774, 1228, 3776, 1226, 4687, 1224, 4227, 244, 3783, 1220, 3784, 1218, 3786, 1216, 4837, 1214, 3807, 48, 3793, 1210, 3794, 1208, 3796, 1206, 4772, 1204, 3822, 240, 3803, 1200, 3804, 1198, 3806, 1196, 3832, 1194, 3842, 238, 3813, 1190, 3814, 1188, 3816, 1186, 3847, 1184, 4747, 236, 3823, 1180, 3824, 1178, 3826, 1176, 3867, 1174, 4782, 234, 3833, 1170, 3834, 1168, 3836, 1166, 3962, 1164, 3882, 46, 3843, 1160, 3844, 1158, 3846, 1156, 4792, 1154, 4062, 230, 3853, 1150, 3854, 1148, 3856, 1146, 4012, 1144, 4957, 228, 3863, 1140, 3864, 1138, 3866, 1136, 4077, 1134, 4742, 226, 3873, 1130, 3874, 1128, 3876, 1126, 3987, 1124, 3932, 224, 3883, 1120, 3884, 1118, 3886, 1116, 4437, 1114, 3947, 44, 3893, 1110, 3894, 1108, 3896, 1106, 3957, 1104, 3967, 220, 3903, 1100, 3904, 1098, 3906, 1096, 3972, 1094, 4387, 218, 3913, 1090, 3914, 1088, 3916, 1086, 4807, 1084, 4612, 216, 3923, 1080, 3924, 1078, 3926, 1076, 4112, 1074, 4007, 214, 3933, 1070, 3934, 1068, 3936, 1066, 4312, 1064, 4022, 8, 3943, 1060, 3944, 1058, 3946, 1056, 4162, 1054, 4032, 210, 3953, 1050, 3954, 1048, 3956, 1046, 4212, 1044, 4047, 208, 3963, 1040, 3964, 1038, 3966, 1036, 4067, 1034, 4252, 206, 3973, 1030, 3974, 1028, 3976, 1026, 4072, 1024, 4237, 204, 3983, 1020, 3984, 1018, 3986, 1016, 4092, 1014, 4127, 40, 3993, 1010, 3994, 1008, 3996, 1006, 4097, 1004, 4737, 200, 4003, 1000, 4004, 501, 4006, 503, 4832, 505, 4287, 101, 4013, 509, 4014, 511, 4016, 513, 4132, 515, 4707, 103, 4023, 519, 4024, 521, 4026, 523, 4912, 525, 4147, 105, 4033, 529, 4034, 531, 4036, 533, 4157, 535, 4167, 21, 4043, 539, 4044, 541, 4046, 543, 4362, 545, 4172, 109, 4053, 549, 4054, 551, 4056, 553, 4192, 555, 4847, 111, 4063, 559, 4064, 561, 4066, 563, 4462, 565, 4697, 113, 4073, 569, 4074, 571, 4076, 573, 4887, 575, 4962, 115, 4083, 579, 4084, 581, 4086, 583, 4232, 585, 4242, 23, 4093, 589, 4094, 591, 4096, 593, 4637, 595, 4247, 119, 4103, 599, 4104, 601, 4106, 603, 4267, 605, 4947, 121, 4113, 609, 4114, 611, 4116, 613, 4272, 615, 4692, 123, 4123, 619, 4124, 621, 4126, 623, 4282, 625, 4292, 125, 4133, 629, 4134, 631, 4136, 633, 4297, 635, 4987, 25, 4143, 639, 4144, 641, 4146, 643, 4317, 645, 4872, 129, 4153, 649, 4154, 651, 4156, 653, 4902, 655, 4332, 131, 4163, 659, 4164, 661, 4166, 663, 4977, 665, 4377, 133, 4173, 669, 4174, 671, 4176, 673, 4357, 675, 4367, 135, 4183, 679, 4184, 681, 4186, 683, 4372, 685, 4777, 5, 4193, 689, 4194, 691, 4196, 693, 4392, 695, 4892, 139, 4203, 699, 4204, 701, 4206, 703, 4992, 705, 4652, 141, 4213, 709, 4214, 711, 4216, 713, 3902, 715, 4402, 143, 4223, 719, 4224, 721, 4226, 723, 4827, 725, 4432, 145, 4233, 729, 4234, 731, 4236, 733, 4897, 735, 4552, 29, 4243, 739, 4244, 741, 4246, 743, 4752, 745, 4457, 149, 4253, 749, 4254, 751, 4256, 753, 4427, 755, 4472, 151, 4263, 759, 4264, 761, 4266, 763, 4492, 765, 4657, 153, 4273, 769, 4274, 771, 4276, 773, 4497, 775, 4702, 155, 4283, 779, 4284, 781, 4286, 783, 4480, 785, 4455, 31, 4293, 789, 4294, 791, 4296, 793, 4990, 795, 4660, 159, 4303, 799, 4304, 801, 4306, 803, 4970, 805, 4997, 161, 4313, 809, 4314, 811, 4316, 813, 4950, 815, 4647, 163, 4323, 819, 4324, 821, 4326, 823, 4940, 825, 4530, 165, 4333, 829, 4334, 831, 4336, 833, 4920, 835, 4915, 33, 4343, 839, 4344, 841, 4346, 843, 4900, 845, 4605, 169, 4353, 849, 4354, 851, 4356, 853, 4705, 855, 4942, 171, 4363, 859, 4364, 861, 4366, 863, 4870, 865, 4642, 173, 4373, 869, 4374, 871, 4376, 873, 4850, 875, 4755, 175, 4383, 879, 4384, 881, 4386, 883, 4780, 885, 4825, 35, 4393, 889, 4394, 891, 4396, 893, 4820, 895, 4815, 179, 4403, 899, 4404, 901, 4406, 903, 4855, 905, 4795, 181, 4413, 909, 4414, 911, 4416, 913, 4775, 915, 4927, 183, 4423, 919, 4424, 921, 4426, 923, 4765, 925, 4805, 185, 4433, 929, 4434, 931, 4436, 933, 4745, 935, 4740, 1, 4443, 939, 4444, 941, 4446, 943, 4725, 945, 4980, 189, 4453, 949, 4454, 951, 4456, 953, 4930, 955, 4700, 191, 4463, 959, 4464, 961, 4466, 963, 4690, 965, 4502, 193, 4473, 969, 4474, 971, 4476, 973, 4860, 975, 4670, 195, 4483, 979, 4484, 981, 4486, 983, 4835, 985, 4650, 39, 4493, 989, 4494, 991, 4496, 993, 4645, 995, 4640, 199, 4503, 999, 4504, 2487, 4506, 2482, 4460, 2472, 4620, 492, 4513, 2452, 4514, 2442, 4516, 2432, 4600, 2422, 4607, 482, 4523, 2402, 4524, 2392, 4526, 2382, 4590, 2372, 4735, 472, 4533, 2352, 4534, 2342, 4536, 2332, 4570, 2322, 4565, 92, 4543, 2302, 4544, 2292, 4546, 2282, 4550, 2272, 4635, 452, 4553, 2252, 4554, 2242, 4556, 2232, 4585, 2222, 4525, 442, 4563, 2202, 4564, 2192, 4566, 2182, 4515, 2172, 4597, 432, 4573, 2152, 4574, 2142, 4576, 2132, 4535, 2122, 4495, 422, 4583, 2102, 4584, 2092, 4586, 2082, 4475, 2072, 4385, 82, 4593, 2052, 4594, 2042, 4596, 2032, 4465, 2022, 4910, 402, 4603, 2002, 4604, 1992, 4606, 1982, 4445, 1972, 3787, 392, 4613, 1952, 4614, 1942, 4616, 1932, 4425, 1922, 4592, 382, 4623, 1902, 4624, 1892, 4626, 1882, 4420, 1872, 4375, 372, 4633, 1852, 4634, 1842, 4636, 1832, 4935, 1822, 4395, 72, 4643, 1802, 4644, 1792, 4646, 1782, 4365, 1772, 4105, 352, 4653, 1752, 4654, 1742, 4656, 1732, 4340, 1722, 4195, 342, 4663, 1702, 4664, 1692, 4666, 1682, 4345, 1672, 4327, 332, 4673, 1652, 4674, 1642, 4676, 1632, 4205, 1622, 4335, 322, 4683, 1602, 4684, 1592, 4686, 1582, 4305, 1572, 4155, 2, 4693, 1552, 4694, 1542, 4696, 1532, 4295, 1522, 4245, 302, 4703, 1502, 4704, 1492, 4706, 1482, 4275, 1472, 4270, 292, 4713, 1452, 4714, 1442, 4716, 1432, 4330, 1422, 4602, 282, 4723, 1402, 4724, 1392, 4726, 1382, 4265, 1372, 4230, 272, 4733, 1352, 4734, 1342, 4736, 1332, 4225, 1322, 4220, 52, 4743, 1302, 4744, 1292, 4746, 1282, 4320, 1272, 4110, 252, 4753, 1252, 4754, 1242, 4756, 1232, 4135, 1222, 4180, 242, 4763, 1202, 4764, 1192, 4766, 1182, 4170, 1172, 4557, 232, 4773, 1152, 4774, 1142, 4776, 1132, 4100, 1122, 4210, 222, 4783, 1102, 4784, 1092, 4786, 1082, 4130, 1072, 4140, 42, 4793, 1052, 4794, 1042, 4796, 1032, 4120, 1022, 4095, 202, 4803, 1002, 4804, 507, 4806, 517, 4080, 527, 3955, 107, 4813, 547, 4814, 557, 4816, 567, 3935, 577, 4547, 117, 4823, 597, 4824, 607, 4826, 617, 4030, 627, 4035, 127, 4833, 647, 4834, 657, 4836, 667, 4040, 677, 3940, 27, 4843, 697, 4844, 707, 4846, 717, 3995, 727, 4025, 147, 4853, 747, 4854, 757, 4856, 767, 3965, 777, 3827, 157, 4863, 797, 4864, 807, 4866, 817, 4060, 827, 4542, 167, 4873, 847, 4874, 857, 4876, 867, 3980, 877, 4065, 177, 4883, 897, 4884, 907, 4886, 917, 3960, 927, 3970, 7, 4893, 947, 4894, 957, 4896, 967, 3850, 977, 3885, 197, 4903, 997, 4904, 2437, 4906, 2412, 3855, 2362, 3915, 462, 4913, 2262, 4914, 2212, 4916, 2162, 3875, 2112, 4627, 412, 4923, 2012, 4924, 1962, 4926, 1912, 3870, 1862, 3890, 362, 4933, 1762, 4934, 1712, 4936, 1662, 3905, 1612, 3900, 12, 4943, 1512, 4944, 1462, 4946, 1412, 3815, 1362, 3840, 262, 4953, 1262, 4954, 1212, 4956, 1162, 3820, 1112, 3800, 212, 4963, 1012, 4964, 537, 4966, 587, 3805, 637, 4677, 137, 4973, 737, 4974, 787, 4976, 837, 3785, 887, 3760, 37, 4983, 987, 4984, 2187, 4986, 2062, 3765, 1812, 3775, 62, 4993, 1312, 4994, 1062, 4996, 687, 3756, 187, 3751, 312, 2501};
        Comparable[] origin = new Comparable[5000];
        for (int i = 0; i < 5000; i++) {
            origin[i] = i;
        }
        List r = Arrays.asList(new Comparable[][][]{
                {origin, expected},
                {{8, 7, 10, 9, 6, 5, 3, 2, 4},
                        {6, 9, 4, 8, 2, 7, 5, 10, 3}},
                {{8, 7, 10, 6, 9, 5, 3, 2, 4, 1},
                        {5, 8, 3, 10, 2, 9, 4, 6, 1, 7}},
                {{1, 5, 1, 1, 6, 4},
                        {1, 6, 1, 4, 1, 5}},
                {{4, 5, 5, 6},
                        {5, 6, 4, 5}},
                {{2, 4, 5, 1, 2, 4, 1, 1, 3, 3, 1, 2, 4, 3},
                        {2, 4, 2, 4, 2, 3, 1, 5, 1, 3, 1, 3, 1, 4}},
                {{0, 1, 2, 3, 4, 5},
                        {2, 3, 1, 4, 0, 5}},
                {{1, 1, 2, 1, 2, 2, 1},
                        {1, 2, 1, 2, 1, 2, 1}},
                {{4, 5, 5, 6},
                        {5, 6, 4, 5}},
                {{4, 5, 5, 5, 5, 6, 6, 6},
                        {5, 6, 5, 6, 5, 6, 4, 5}},
        });
        return r;
    }

    private Comparable[] arr;
    private Comparable[] sorted;

    private int[] toInt(Comparable[] arr) {
        int[] r = new int[arr.length];
        int i = 0;
        for (Comparable c : arr) {
            r[i++] = (Integer) c;
        }
        return r;
    }

    public WinggleSortTest(Comparable[] arr, Comparable[] sorted) {
        this.arr = arr;
        this.sorted = sorted;
    }

    @Test(timeout = 100l, expected = Test.None.class)
    public void winggleSortTest() {
        int[] ar = toInt(Arrays.copyOf(arr, arr.length));
        // start test
        Leetcode324WiggleSortII.wiggleSort(ar);
        int[] sortedNow = toInt(sorted);
        Assert.assertTrue(Arrays.equals(sortedNow, ar));
    }
}
