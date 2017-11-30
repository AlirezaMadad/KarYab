package com.arsan.niloufar.karyab;

/**
 * Created by Niloufar on 4/17/2016.
 */
public class Enums {
    public static enum Gender{
        Male(1),
        Female(0);
        private final int Value;
        private Gender(int value){
            this.Value=value;
        }
        public static String ToString(int i){
            if(i == 1){
               return "جناب آقای";
            }
            if(i == 0){
                return "سرکار خانم";
            }
            return "";
        }
    }
    public static enum Usage{
        Employer(1),
        JobSeeker(0);
        private final int Value;
        private Usage(int value){
            this.Value=value;
        }
        public static String ToString(int i){
            if(i == 1){
                return "کارفرما";
            }
            if(i == 0){
                return "کارجو";
            }
            return "";
        }
    }
    public static enum Efficieny{
        Bad(0),
        Medium(1),
        Good(2),
        Excellent1(3);
        private final int Value;
        private Efficieny(int value){
            this.Value=value;
        }
        public static String ToString(int i){
            if(i == 0){
                return "نا مناسب";
            }
            if(i == 1){
                return "معمولی";
            }
            if(i == 2){
                return "خوب";
            }
            if(i == 3){
                return "عالی";
            }
            return "";
        }
    }
    public static enum Duration{
        LessThanMont(0),
        ToSixMonth(1),
        ToOneYear(2),
        MoreThanAYear(3);
        private final int Value;
        private Duration(int value){
            this.Value=value;
        }
        public static String ToString(int i){
            if(i == 0){
                return "کمتر از یک ماه";
            }
            if(i == 1){
                return "بین یک تا 6 ماه";
            }
            if(i == 2){
                return "از 6 ماه تا یکسال";
            }
            if(i == 3){
                return "بیشتر از یک سال";
            }
            return "";
        }
    }

}
