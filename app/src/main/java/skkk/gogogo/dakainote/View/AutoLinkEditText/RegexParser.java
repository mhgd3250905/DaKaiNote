package skkk.gogogo.dakainote.View.AutoLinkEditText;

/**
 * Created by admin on 2016/10/9.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/9$ 12:23$.
*/
class RegexParser {

    static final String PHONE_PATTERN = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
    static final String EMAIL_PATTERN = "([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})";
    static final String HASHTAG_PATTERN = "(?:^|\\s|$)#[\\p{L}0-9_]*";
    static final String MENTION_PATTERN = "(?:^|\\s|$|[.])@[\\p{L}0-9_]*";
    static final String URL_PATTERN = "(^|[\\s.:;?\\-\\]<\\(])" +
            "((https?://|www\\.|pic\\.)[-\\w;/?:@&=+$\\|\\_.!~*\\|'()\\[\\]%#,☺]+[\\w/#](\\(\\))?)" +
            "(?=$|[\\s',\\|\\(\\).:;?\\-\\[\\]>\\)])";
}
