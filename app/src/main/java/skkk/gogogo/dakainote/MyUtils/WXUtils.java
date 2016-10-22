package skkk.gogogo.dakainote.MyUtils;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;

/**
 * Created by admin on 2016/10/22.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/22$ 9:21$.
*/
public class WXUtils {

    public static boolean shareTextToWXSceneTimeliness(String content,IWXAPI api,int shareWhere){
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = content;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = content;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis()+"text"; // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene =shareWhere;

        // 调用api接口发送数据到微信
        boolean result=api.sendReq(req);
        return result;
    }



}
