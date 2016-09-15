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
-
1. 当edit为空了之后才可以上一个item（Image/Voice）才可以获取焦点；
2. 每一个item（Image/Voice）都可以通过key监听来进行删除动作

完成了一个基础版本的删除设计 如下

![](http://i.imgur.com/nebFhdH.gif)

#2016/9/9 16:56:44 

- 对于编辑页面当呼出键盘的时候 activity自动适应布局获得更好的交互
- 接下来要实现的是批量删除的样式设计无非就是长按进入编辑模式
- 
1. 首先应该每个item有gone一个checkbox
2. 然后有一个flag当长按的时候进入编辑模式所有的checkbox显示出来
3. 然后对于每个item的选中应该都有一个id去记录
4. 最后遍历删除


----------


##2016/9/10 12:23:53 
借鉴一下随笔记得布局风格

- 针对Note界面
-

1. note中就一个EditText
2. 所有的图片都放在下面一个可以滑动的RecyclerView中
3. 所有Voice也都放在一个可以滑动的RecyclerView中



- note中图片的保存
-
1. 拍照的时候回保存图片到内存中，可以获取图片的路径，当然每一个图片
2. 将一个note中的图片保存在缓存数据库中 以 路径-notekey的形式
3. 对于显示图片的recyclerView通过查找noyekey的方式从图片缓存数据库中获取到，然后显示
4. 保存的时候直接将缓存数据库中的图片保存起来

-note中图片的显示
-
1 所有的图片都保存在和note向关联的数据库image中
2 在加载完et内容之后，提取出所有的对应的图片条目
3 首先保存到图片缓存数据库中
4 然后从缓存数据库中提取出所有的图片然后显示到recyclerView

2016/9/10 23:53:47 
完成了基本的图片的保存以及显示，如下
![](http://i.imgur.com/aiWPp8n.png)

----------


#2016/9/11 23:05:26 

这个周末终于算是完成了note的图片和文字的显示以及编辑 

当然还有返回按钮保存的功能

感觉自己有点傻，对于触摸返回按键就可以对note进行保存的功能：

其实只需要修改onBackPress就可以了呀~


----------

#2016/9/12 20:44:25

今天需要按照image的显示方式吧voice加入进去
-

首先我们整理一下voice的存储以及显示方式

1. 首先需要一个RecyclerView来显示Voice
2. 然后item就是一个AudioButton
3. 点击事件沿用RadioButton自带的方法就可以

感觉还不错~


----------

#2016/9/13 21:19:03 


今天需要先解决编辑框在fl_image和fl_voice Gone之后布局自适应的问题。
通过对布局文件的addOnLayoutChangeListener进行监听 配合handler就可做出对应的反应
接下来要做的就是舍去我可爱的弹射菜单了~，下面整理一下需要的功能

1. 这是一个布局在页面最下方的横向布局
2. 这个布局会随着软件盘的出现和隐藏做出相应的位置变化
玩成~
感觉还不错~

----------
#2016/9/14 21:23:56 


今天要做的是点击菜单image图标弹出dialog：a 拍照 b 来自相册
分析一下需要实现的功能:

1. 弹出dialog的样式
2. 如何打开相册并从相册中选择图片（主要是获取imagePath）

完成！

后面亟待完成的就是代办是待办事项的点击加入了

1.点击图片之后删除是否需要联动到内存图片
2.list删除的要有删除提示

完成！

----------

#2016/9/15 13:33:00 

今天要完成额的及时待办事项，这里分析一下功能描述：
1. 点击插入待办事项
2. 待办事项item形式为checkbox+EditText
3. item需要在一个RecvclerView中显示吗？
4. 待办事项item要具备添加 删除的功能






 




