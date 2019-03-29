package com.ccbfm.journal.bearer;

class BearerTools {

    private static final String TOP = "----------------------------------------------------------------------------------";
    private static final String BOTTOM = TOP;
    private static final String LEFT = "|";
    private static final String PREFIX = "-";

    private static void handleLineFeed(StringBuilder sb){
        sb.append(" \n");
    }

    static void handleTop(StringBuilder sb){
        handleLineFeed(sb);
        sb.append(LEFT);
        sb.append(TOP);
    }

    static void handleMessage(StringBuilder sb, String message){
        handleLineFeed(sb);
        sb.append(LEFT);
        sb.append(PREFIX);
        sb.append(message);
    }

    static void handleBottom(StringBuilder sb){
        handleLineFeed(sb);
        sb.append(LEFT);
        sb.append(BOTTOM);
    }

    static void appendParameters(StringBuilder stringBuilder, String[] parameterNames, Object[] parameterValues){
        for (int i = 0; i < parameterValues.length; i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(parameterNames[i]).append("=");
            stringBuilder.append(parameterValues[i]);
        }
    }
}
