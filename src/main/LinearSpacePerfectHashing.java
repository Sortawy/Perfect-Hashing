package src.main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LinearSpacePerfectHashing <T> implements HashTable<T> {
    private UniversalHashing<T> firstLevelHashFunction;
    private UniversalHashing<T>[] secondLevelHashFunctions;
    private T[] keys;
    private int numberOfKeys;
    private int numberOfInsertions;
    private int numberOfDeletions;
    private int numberOfCollisions;
    private int[] firstLevelHashTable;
    private T[][] secondLevelHashTables;

    public LinearSpacePerfectHashing () {
        this.firstLevelHashTable = new int[10];
        this.secondLevelHashTables = (T[][]) new Object[10][];
        this.firstLevelHashFunction = new UniversalHashing<T>(10);
        this.secondLevelHashFunctions = (UniversalHashing<T>[]) new UniversalHashing<?>[10];
        this.numberOfInsertions = 0;
        this.numberOfDeletions = 0;
        this.numberOfCollisions = 0;
        this.numberOfKeys = 0;
        this.keys = (T[]) new Object[0];
    }

    public LinearSpacePerfectHashing (T[] keys) {
        this.firstLevelHashTable = new int[10];
        this.secondLevelHashTables = (T[][]) new Object[keys.length][];
        this.firstLevelHashFunction = new UniversalHashing<T>(keys.length);
        this.secondLevelHashFunctions = (UniversalHashing<T>[]) new UniversalHashing<?>[keys.length];
        this.numberOfInsertions = 0;
        this.numberOfDeletions = 0;
        this.numberOfCollisions = 0;
        this.numberOfKeys = keys.length;
        this.keys = keys;
        this.buildHashTable(keys);
    }

    private void buildHashTable (T[] keys) {
        // this.firstLevelHashTable = new int[numberOfKeys];
        // this.secondLevelHashTables = (T[][]) new Object[numberOfKeys][];
        // this.firstLevelHashFunction = new UniversalHashing<T>(this.numberOfKeys);
        // this.secondLevelHashFunctions = (UniversalHashing<T>[]) new UniversalHashing<?>[this.numberOfKeys];
        // this.secondLevelHashFunctions = (UniversalHashing<T>[]) new UniversalHashing<?>[10];
        this.buildFirstLevelHashTable(keys);
        this.buildSecondLevelHashTables(keys);
    }

    private void buildFirstLevelHashTable (T[] keys) {
        for(int i=0; i<this.numberOfKeys; i++){
            T key = keys[i];
            int firstLevelIndex = this.firstLevelHashFunction.hash(key);
            this.firstLevelHashTable[firstLevelIndex]++;
        }
    }

    private void buildSecondLevelHashTables (T[] keys) {
        for(int firstLevelIndex=0; firstLevelIndex<this.secondLevelHashTables.length; firstLevelIndex++){
            if(firstLevelHashTable[firstLevelIndex] == 0) continue;
            //int size = this.firstLevelHashTable[firstLevelIndex]*this.firstLevelHashTable[firstLevelIndex];
            // this.secondLevelHashTables[firstLevelIndex] =  (T[]) new Object[size*size];
            // this.secondLevelHashFunctions[firstLevelIndex] = new UniversalHashing<T>(size*size);
            T[] collisionKeys = this.getCollisionKeys(firstLevelIndex);
            this.buildHashTableEntry(collisionKeys, firstLevelIndex);
        }
    }

    private T[] getCollisionKeys (int firstLevelIndex) {
        T[] collisionKeys = (T[]) new Object[this.firstLevelHashTable[firstLevelIndex]];
        int index = 0;
        Set<T> set = new HashSet<>();
        for (int i = 0; i < this.numberOfKeys; i++) {
            T key = keys[i];
            if (this.firstLevelHashFunction.hash(key) == firstLevelIndex && set.add(key)) {
                collisionKeys[index++] = key;
            }
        }
        return Arrays.copyOf(collisionKeys, index);
    }


    private void buildHashTableEntry (T[] collisionKeys, int firstLevelIndex) {
        int size = collisionKeys.length;
        this.secondLevelHashTables[firstLevelIndex] =  (T[]) new Object[size*size];
        this.secondLevelHashFunctions[firstLevelIndex] = new UniversalHashing<T>(size*size);

        while(true){
            boolean[] isUsed = new boolean[size*size];
            boolean isGood = true;
            for(int i=0; i<size; i++){
                T key = collisionKeys[i];
                if(key == null) continue;
                int secondLevelIndex = this.secondLevelHashFunctions[firstLevelIndex].hash(key);
                if(isUsed[secondLevelIndex]){
                    isGood = false;
                    break;
                }
                isUsed[secondLevelIndex] = true;
                this.secondLevelHashTables[firstLevelIndex][secondLevelIndex] = key;
            }
            if(isGood) break;
            this.numberOfCollisions++;
            this.secondLevelHashFunctions[firstLevelIndex].regenerateHashFunction();
        }
    }

    private void rehashTableEntry (int firstLevelIndex, T key) {
        this.numberOfCollisions++;
        this.secondLevelHashFunctions[firstLevelIndex].regenerateHashFunction();
        T[] collisionKeys = this.getCollisionKeys(firstLevelIndex);
        this.buildHashTableEntry(collisionKeys, firstLevelIndex);
    }


    public void batchInsert (T[] keys) {
        this.keys = Arrays.copyOf(this.keys, this.keys.length+keys.length);
        for(int i=0; i<keys.length; i++){
            this.keys[i+this.keys.length-keys.length] = keys[i];
        }
        this.numberOfKeys = this.keys.length;
        this.buildHashTable(this.keys);
    }

    public void insert (T key) {
        if(this.contains(key)) return;
        this.numberOfInsertions++;
        this.numberOfKeys++;

        this.keys = Arrays.copyOf(this.keys, this.keys.length+1);
        this.keys[this.keys.length-1] = key;

        int firstLevelIndex = this.firstLevelHashFunction.hash(key);
        int secondLevelIndex = 0;
        this.firstLevelHashTable[firstLevelIndex]++;

        if(this.secondLevelHashTables[firstLevelIndex] == null){
            this.secondLevelHashTables[firstLevelIndex] = (T[]) new Object[1];
            this.secondLevelHashFunctions[firstLevelIndex] = new UniversalHashing<T>(1);
            this.secondLevelHashTables[firstLevelIndex][0] = key;
        }else if (this.secondLevelHashTables[firstLevelIndex][secondLevelIndex] == null){
            this.secondLevelHashTables[firstLevelIndex][secondLevelIndex] = key;
        }else{
            this.rehashTableEntry(firstLevelIndex, key);
        }
    }

    public void delete (T key) {
        if(!this.contains(key)) return;
        this.numberOfDeletions++;
        int firstLevelIndex = this.firstLevelHashFunction.hash(key);
        int secondLevelIndex = this.secondLevelHashFunctions[firstLevelIndex].hash(key);
        this.secondLevelHashTables[firstLevelIndex][secondLevelIndex] = null;
        this.numberOfKeys--;
    }

    public boolean contains (T key) {
        try {
            int firstLevelIndex = this.firstLevelHashFunction.hash(key);
            int secondLevelIndex = this.secondLevelHashFunctions[firstLevelIndex].hash(key);
            return key.equals(this.secondLevelHashTables[firstLevelIndex][secondLevelIndex]);
        } catch (Exception e) {
            return false;
        }
    }
    
    public int getNumberOfCollisions () {
        return this.numberOfCollisions;
    }

    @Override
    public int getNumberOfItems(){
        return this.numberOfKeys;
    }
    
    public static void main(String[] args) {
        LinearSpacePerfectHashing<Integer> lsh = new LinearSpacePerfectHashing<>();
        Integer[] keys = {10, 22, 3, 4, 5, 100, 6, 77, 8, 9, 100, 1010, 1022, 103, 104, 105, 106, 1077, 108, 109, 220, 2210, 2222, 223, 224, 225, 226, 2277, 228, 229, 30, 310, 322, 33, 34, 35, 36, 377, 38, 39, 40, 410, 422, 43, 44, 45, 46, 477, 48, 49, 50, 510, 522, 53, 54, 55, 56, 577, 58, 59, 60, 610, 622, 63, 64, 65, 66, 677, 68, 69, 770, 7710, 7722, 773, 774, 775, 776, 7777, 778, 779, 80, 810, 822, 83, 84, 85, 86, 877, 88, 89, 90, 910, 922, 93, 94, 95, 96, 977, 98, 99, 1000, 10010, 10022, 1003, 1004, 1005, 1006, 10077, 1008, 1009, 10100, 101010, 101022, 10103, 10104, 10105, 10106, 101077, 10108, 10109, 10220, 102210, 102222, 10223, 10224, 10225, 10226, 102277, 10228, 10229, 1030, 10310, 10322, 1033, 1034, 1035, 1036, 10377, 1038, 1039, 1040, 10410, 10422, 1043, 1044, 1045, 1046, 10477, 1048, 1049, 1050, 10510, 10522, 1053, 1054, 1055, 1056, 10577, 1058, 1059, 1060, 10610, 10622, 1063, 1064, 1065, 1066, 10677, 1068, 1069, 10770, 107710, 107722, 10773, 10774, 10775, 10776, 107777, 10778, 10779, 1080, 10810, 10822, 1083, 1084, 1085, 1086, 10877, 1088, 1089, 1090, 10910, 10922, 1093, 1094, 1095, 1096, 10977, 1098, 1099, 2200, 22010, 22022, 2203, 2204, 2205, 2206, 22077, 2208, 2209, 22100, 221010, 221022, 22103, 22104, 22105, 22106, 221077, 22108, 22109, 22220, 222210, 222222, 22223, 22224, 22225, 22226, 222277, 22228, 22229, 2230, 22310, 22322, 2233, 2234, 2235, 2236, 22377, 2238, 2239, 2240, 22410, 22422, 2243, 2244, 2245, 2246, 22477, 2248, 2249, 2250, 22510, 22522, 2253, 2254, 2255, 2256, 22577, 2258, 2259, 2260, 22610, 22622, 2263, 2264, 2265, 2266, 22677, 2268, 2269, 22770, 227710, 227722, 22773, 22774, 22775, 22776, 227777, 22778, 22779, 2280, 22810, 22822, 2283, 2284, 2285, 2286, 22877, 2288, 2289, 2290, 22910, 22922, 2293, 2294, 2295, 2296, 22977, 2298, 2299, 300, 3010, 3022, 303, 304, 305, 306, 3077, 308, 309, 3100, 31010, 31022, 3103, 3104, 3105, 3106, 31077, 3108, 3109, 3220, 32210, 32222, 3223, 3224, 3225, 3226, 32277, 3228, 3229, 330, 3310, 3322, 333, 334, 335, 336, 3377, 338, 339, 340, 3410, 3422, 343, 344, 345, 346, 3477, 348, 349, 350, 3510, 3522, 353, 354, 355, 356, 3577, 358, 359, 360, 3610, 3622, 363, 364, 365, 366, 3677, 368, 369, 3770, 37710, 37722, 3773, 3774, 3775, 3776, 37777, 3778, 3779, 380, 3810, 3822, 383, 384, 385, 386, 3877, 388, 389, 390, 3910, 3922, 393, 394, 395, 396, 3977, 398, 399, 400, 4010, 4022, 403, 404, 405, 406, 4077, 408, 409, 4100, 41010, 41022, 4103, 4104, 4105, 4106, 41077, 4108, 4109, 4220, 42210, 42222, 4223, 4224, 4225, 4226, 42277, 4228, 4229, 430, 4310, 4322, 433, 434, 435, 436, 4377, 438, 439, 440, 4410, 4422, 443, 444, 445, 446, 4477, 448, 449, 450, 4510, 4522, 453, 454, 455, 456, 4577, 458, 459, 460, 4610, 4622, 463, 464, 465, 466, 4677, 468, 469, 4770, 47710, 47722, 4773, 4774, 4775, 4776, 47777, 4778, 4779, 480, 4810, 4822, 483, 484, 485, 486, 4877, 488, 489, 490, 4910, 4922, 493, 494, 495, 496, 4977, 498, 499, 500
        };
        lsh.batchInsert(keys);
        lsh.insert(10);
        lsh.insert(2);
        lsh.insert(3);
        lsh.insert(4);
        lsh.insert(5);
        lsh.insert(10);
        lsh.insert(6);
        lsh.insert(77);
        lsh.insert(8);
        lsh.insert(9);
        lsh.insert(1);
        lsh.insert(2);
        lsh.insert(3);
        lsh.insert(4);
        lsh.insert(5);
        lsh.insert(10);
        lsh.insert(6);
        lsh.insert(7);
        lsh.insert(8);
        lsh.insert(9);
        lsh.insert(1);
        lsh.insert(2);
        lsh.insert(3);
        lsh.insert(4);
        lsh.insert(5);
        lsh.insert(10);
        lsh.insert(6);
        lsh.insert(7);
        lsh.insert(8);
        lsh.insert(9);
        lsh.insert(1);
        lsh.insert(2);
        lsh.insert(3);
        lsh.insert(4);
        lsh.insert(5);
        lsh.insert(10);
        lsh.insert(6);
        lsh.insert(7);
        lsh.insert(8);
        lsh.insert(9);
        lsh.insert(1);
        lsh.insert(2);
        lsh.insert(3);
        lsh.insert(4);
        lsh.insert(5);
        lsh.insert(10);
        lsh.insert(6);
        lsh.insert(7);
        lsh.insert(8);
        lsh.insert(9);
        lsh.insert(1);
        lsh.insert(2);
        lsh.insert(3);
        lsh.insert(4);
        lsh.insert(5);
        lsh.insert(10);
        lsh.insert(6);
        lsh.insert(7);
        lsh.insert(8);
        lsh.insert(9);

    
        System.out.println(lsh.contains(1)); // 0
        lsh.delete(1);
        System.out.println(lsh.contains(1)); // 0
        System.out.println("-------------------------------------------------"); // 0
        System.out.println(lsh.contains(0)); // 1
        lsh.insert(0);
        System.out.println(lsh.contains(0)); // 1
        System.out.println("-------------------------------------------------"); // 1
        Integer[] keyss = {1, 2, 3, 4, 5, 10, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426, 427, 428, 429, 430, 431, 432, 433, 434, 435, 436, 437, 438, 439, 440, 441, 442, 443, 444, 445, 446, 447, 448, 449, 450, 451, 452, 453, 454, 455, 456, 457, 458, 459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470, 471, 472, 473, 474, 475, 476, 477, 478, 479, 480, 481, 482, 483, 484, 485, 486, 487, 488, 489, 490, 491, 492, 493, 494, 495, 496, 497, 498, 499, 500
        };
        lsh.batchInsert(keyss);
        System.out.println(lsh.contains(50)); // 1
        System.out.println(lsh.contains(1550)); // 1
    }
    
}