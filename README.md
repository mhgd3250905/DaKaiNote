# DaKaiNote
## 目前比较流行的弹射菜单：
![](http://i.imgur.com/qVlUwYD.gif)

----------
# 大开笔记开发记录
#2016/9/6 20:55:45 

今天要解决的问题是：
- note编辑界面的后退按键的功能完善
	
1. 当edititem文字为空的时候，就应该删除当前view
2. 当下一个item是image或者voice的时候应该有一个选择特效 或者一个dialog

卒~

- 对于主界面中添加了无note时背景提示
- ![](http://i.imgur.com/HqkGWaz.png)

----------
#2016/9/9 13:21:30 

今天要解决的问题是解决voice 模块的删除问题

- 完善自定义View（Voice） view上自带删除按钮
下面列出入需要实现的目标
1. 当edit为空了之后才可以上一个item（Image/Voice）才可以获取焦点；
2. 每一个item（Image/Voice）都可以通过key监听来进行删除动作

完成了一个基础版本的删除设计 如下
![](http://i.imgur.com/nebFhdH.gif)
