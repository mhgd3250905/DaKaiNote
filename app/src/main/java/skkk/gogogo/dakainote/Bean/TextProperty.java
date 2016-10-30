package skkk.gogogo.dakainote.Bean;

/**
 * Created by admin on 2016/10/30.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/30$ 14:55$.
*/
public class TextProperty {
    private int heigt;      //读入文本的行数
    private String[] context = new String[1024];    //存储读入的文本

    /*
     *@parameter wordNum  设置每行显示的字数
     * 构造函数将文本读入，将每行字符串切割成小于等于35个字符的字符串  存入字符数组
     *
     */
    public TextProperty(int wordNum, String content) throws Exception {
        int i = 0;
        if (content.length() > wordNum) {
            int k = 0;
            while (k + wordNum <= content.length()) {
                context[i++] = content.substring(k, k + wordNum);
                k = k + wordNum;
            }
            context[i++] = content.substring(k, content.length());
        } else {
            context[i++] = content;
        }

        this.heigt = i;
    }

    public int getHeigt() {
        return heigt;
    }

    public String[] getContext() {

        return context;
    }
}