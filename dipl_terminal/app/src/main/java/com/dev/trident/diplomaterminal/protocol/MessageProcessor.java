package com.dev.trident.diplomaterminal.protocol;

/**
 * trident 16.05.16.
 */
public class MessageProcessor {
    public void processMessage(String message,MessageCallback callback){
        if(message.equals("")) {
            callback.onError(MessageCallback.ERROR_SYNTAX);
            return;
        }

        String[] parts = message.split("\\!");

        MessageType type = getMessageType(parts[0]);
        if(type!=null){
            switch (type){
                case LOGIN_ANSWER:
                    switch (message){
                        case "la!s=a":
                            callback.onLoginAnswer(MessageCallback.ACCESS_ALLOWED);
                            break;
                        case "la!s=d":
                            callback.onLoginAnswer(MessageCallback.ACCESS_DENIED);
                            break;
                        case "la!s=s":
                            callback.onLoginAnswer(MessageCallback.UNLOCK_FOR_ADMIN);
                            break;
                        default:
                            callback.onError(MessageCallback.ERROR_SYNTAX);
                            break;
                    }
                    break;
                case ACTION:
                    switch (message){
                        case "a!p=u":
                            callback.onAction(MessageCallback.UNLOCK_SYSTEM);
                            break;
                        case "a!p=l":
                            callback.onAction(MessageCallback.LOCK_SYSTEM);
                            break;
                        case "a!p=a":
                            callback.onAction(MessageCallback.UNLOCK_FOR_ADMIN);
                            break;
                        default:
                            callback.onError(MessageCallback.ERROR_SYNTAX);
                            break;
                    }
                    break;
                case PING:
                    switch (message){
                        case "p!a=c":
                            callback.onPing(MessageCallback.SYSTEM_CORRECT);
                            break;
                        case "p!a=i":
                            callback.onPing(MessageCallback.SYSTEM_INCORRECT);
                            break;
                        default:
                            callback.onError(MessageCallback.ERROR_SYNTAX);
                            break;
                    }
                    break;
                case ERROR:
                    switch (message){
                        case "e!s=l":
                            callback.onError(MessageCallback.ERROR_SYNTAX);
                            break;
                        case "e!s=o":
                        default:
                            callback.onError(MessageCallback.ERROR_OTHER);
                            break;
                    }
                    break;

                case MESSAGE:
                    if(message.toLowerCase().contains("m!p=i")){
                        callback.onMessage(MessageCallback.MESSAGE_INTERNAL,message.split("c=")[1]);
                    } else
                    if(message.toLowerCase().contains("m!p=u")){
                        callback.onMessage(MessageCallback.MESSAGE_FOR_USER,message.split("c=")[1]);
                    } else
                        callback.onError(MessageCallback.ERROR_SYNTAX);
                    break;
            }
        } else callback.onError(MessageCallback.ERROR_SYNTAX);
    }

    private MessageType getMessageType(String prefix){
        for (MessageType type:MessageType.values()){
            if(type.getLabel().equals(prefix))
                return type;
        }
        return null;
    }

    public String createMessage(MessageType type,String[]... paramValues){
        String result = "";
        result+=type.getLabel()+"!";
        if(paramValues!=null&&paramValues.length!=0)
        for (int i = 0;i<paramValues.length;i++){
            if(i!=0) result+="#";
            result+=paramValues[i][0]+"="+paramValues[i][1];
        }
        return result;
    }

    /**
     * trident 16.05.16.
     */
    public static enum MessageType {
        LOGIN_REQUEST("lr",new String[][]{{"c"}}),
        LOGIN_ANSWER("la",new String[][]{{"s","a","d"}}),
        MESSAGE("m",new String[][]{{"p","u","i"},{"c"}}),
        ACTION("a",new String[][]{{"p","l","u","a"}}),
        PING("p",new String[][]{{"a","c","i"}}),
        ERROR("e",new String[][]{{"s","l","o"}});


        private String label;
        private String[][] subparams;

        public String getLabel(){
            return label;
        }

        public String[][] getSubparams(){
            return subparams;
        }

        MessageType(String label,String[][] subparams){
            this.label = label;
            this.subparams = subparams;
        }
    }
}
