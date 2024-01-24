package com.jainmaitri.app;

public class FCMSend {
 /*   private static String BASE_URL = "https://fcm.googleapis.com/fcm/send";
    private static String SERVER_KEY = "AAAAq4oztl8:APA91bHQjF8_WUyxyAJOChwvQLHEQ5MoP4mOXE1MEH1_w-lGznOcHes7T6RIM4N1KXmbkTXp05X-FTLK16Nv2QMylb3OwmHEjGK3fD86irYcyqurUYfEtChcTlGRjQKfNPkVvCFYBgpA";

    public static void SetServerKey(String serverKey) {
        FCMSend.SERVER_KEY = "key=" + serverKey;
    }

    protected String title = null, body = null, to = null, image = null, click_action = null;
    protected HashMap<String, String> datas = null;
    protected boolean topic;
    protected String result;

    public String Result() {
        return result;
    }

    public static class Builder {
        private FCMSend mFcm;

        public Builder(String to) {
            mFcm = new FCMSend();
            mFcm.to = to;
        }

        public Builder(String to, boolean topic) {
            mFcm = new FCMSend();
            mFcm.to = to;
            mFcm.topic = topic;
        }

        public Builder setTitle(String title) {
            mFcm.title = title;
            return this;
        }

        public Builder setBody(String body) {
            mFcm.body = body;
            return this;
        }

        public Builder setImage(String image) {
            mFcm.image = image;
            return this;
        }

        public Builder setClickAaction(String click_action) {
            mFcm.click_action = click_action;
            return this;
        }

        public Builder setData(HashMap<String, String> datas) {
            mFcm.datas = datas;
            return this;
        }

        @SuppressLint("NewApi")
        public FCMSend send() {
            if (SERVER_KEY == null) mFcm.result = "No Server Key";
            else {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    JSONObject json = new JSONObject();
                    if (mFcm.topic) json.put("to", "/topics/" + mFcm.to);
                    else json.put("to", mFcm.to);
                    JSONObject notification = new JSONObject();
                    notification.put("title", mFcm.title);
                    notification.put("body", mFcm.body);
                    if (mFcm.image != null) notification.put("image", mFcm.image);
                    if (mFcm.click_action != null)
                        notification.put("click_action", mFcm.click_action);
                    json.put("notification", notification);

                    if (mFcm.datas != null) {
                        JSONObject data = new JSONObject();
                        mFcm.datas.forEach((key, value) -> {
                            try {
                                data.put(key, value);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                        json.put("data", data);
                    }
JSONObjectRe

                } catch (JSONException | IOException e) {
                    mFcm.result = e.getMessage();
                }
            }
            return mFcm;
        }
    }*/
}
