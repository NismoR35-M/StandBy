package com.blankScreen.standby.utils;

public class Constants {

    public static final class Intent{
        public static final class Extra {
            public static final class OverlayAction {
                public static final String KEY = "overlay_action";
                public static final byte SHOW = 1;
                public static final byte HIDE = 0;
                public static final byte HIDE_IMMEDIATELY = 2;// Value of the extra if one wants to hide the overlay without blending.
                public static final byte NOTHING = -1; // Value of the extra if nothing should happen. Just to have a default.
                public static final byte DEFAULT = NOTHING;
                public static final byte SHOW_NOTIFICATION = 3;
                public static final byte HIDE_NOTIFICATION = 4;

            }
        }
    }
}
