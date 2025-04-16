package com.hms.hotel_booking_system;

import java.util.*;

public class MinmSumSubarrayLength {

        public static void main(String[] args){
            int[] arr = {1,2,3,7,4,5};
            int n = arr.length;
            int target = 17;

            int left = 0;
            int minLength = Integer.MAX_VALUE;
            int sum = 0;
            for(int right = 0;right<n;right++){
                sum+=arr[right];
                while(sum>=target){
                    minLength = Math.min(minLength, right-left+1);
                    sum -= arr[left];
                    left++;
                }
            }
            System.out.println(minLength);
        }
}
