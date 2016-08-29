package skkk.gogogo.dakainote.Interface;

/**
 * Created by admin on 2016/8/29.
 */
/*
* 
* 描    述：录音策略接口
* 作    者：ksheng
* 时    间：2016/8/29$ 21:26$.
*/
public interface RecordStrategy {
    //在这里进行录音准备工作，重置录音文件名等
    public void ready();

    //开始录音
    public void start();

    //录音结束
    public void stop();

    //录音失败时候删除原来的旧文件
    public void deleteOldFile();

    //获取录音音量的大小
    public double getAmplitude();

    //返回录音文件的完整路径
    public String getFilePath();

}
