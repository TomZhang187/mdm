package com.hqhop.modules.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;

public class MessageUtils {


    /**
     *  通过指定应用给制定用户发送消息
     * @param agentId  发消息的应用id
     * @param userid_list 接收者的用户userid列表，最大列表长度：100， 可选(userid_list,dept_id_list, to_all_user必须有一个不能为空) 例如：zhangsan,lisi
     * @param content 消息内容
     * @throws ApiException
     */
   public static void sendWorkMessage(Long agentId,String userid_list, String content) throws ApiException {

       DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

       OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
       request.setUseridList(userid_list);
       request.setAgentId(agentId);
       request.setToAllUser(false);

       OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
       msg.setMsgtype("text");
       msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
       msg.getText().setContent(content);
       request.setMsg(msg);
       OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, DingTalkUtils.getAccessToken());
       Long taskId = response.getTaskId();

   }
    // 4258231637652962
    public static void main(String[] args) {
        try {
            MessageUtils.sendWorkMessage(305599728L,"4258231637652962", "这是一条测试消息");
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
