import re

# pip 包管理工具

r'''
re.match 函数
原型：match(pattern,string,flags=0)
参数：
pattern:匹配的正则表达式
string:要匹配的字符串
flags:标志位，用于控制正则表达式的匹配方式
re.I            忽略大小写
re.L            做本地化识别
re.M            多行匹配，影响^和$
re.S            使.匹配包括换行符在内的所有字符
re.U            根据Unicode字符集解析字符，影响 \w   \W   \b   \B
re.X            使我们以更灵活的格式理解正则表达式
功能：尝试从字符串的起始位置匹配一个模式，如果不是起始位置匹配成功的话，返回None

'''

# www.baidu.com

print(re.match("www", "www.baidu.com"))
print(re.match("www", "ww.baidu.com"))
print(re.match("www", ".baiduwww.com"))
print(re.match("www", "wwW.baidu.com"))
print(re.match("www", "wwW.baidu.com", flags=re.I))

# 扫描整个字符串，返回从起始位置成功的匹配
print("-------------------------------------------------")


r'''
re.search 函数
原型：search(pattern,string,flags=0)
参数：
pattern:匹配的正则表达式
string:要匹配的字符串
flags:标志位，用于控制正则表达式的匹配方式
re.I            忽略大小写
re.L            做本地化识别
re.M            多行匹配，影响^和$
re.S            使.匹配包括换行符在内的所有字符
re.U            根据Unicode字符集解析字符，影响 \w   \W   \b   \B
re.X            使我们以更灵活的格式理解正则表达式
功能：扫描整个字符串，并返回第一个成功的匹配

'''
print(re.search("qyx", "pehw is a good man! qyx is a good man"))


print("---------------------------------------------------")

r'''
re.findall 函数
原型：findall(pattern,string,flags=0)
参数：
pattern:匹配的正则表达式
string:要匹配的字符串
flags:标志位，用于控制正则表达式的匹配方式
re.I            忽略大小写
re.L            做本地化识别
re.M            多行匹配，影响^和$
re.S            使.匹配包括换行符在内的所有字符
re.U            根据Unicode字符集解析字符，影响 \w   \W   \b   \B
re.X            使我们以更灵活的格式理解正则表达式
功能：扫描整个字符串，并返回结果列表
'''

print(re.findall("qyx", "qyx is a good man, qyx is a good man"))

print("--------------------------------------------------------")

# 正则表达式的元字符

print("-------匹配单个字符与数字--------")
r'''
.   匹配除换行符以外的任意字符
[0123456789]    []是字符集合，表示匹配方括号中所包含的任意一个字符
[qyx]   匹配 'q','y','x'中任意一个字符
[a-z]   匹配任意一个小写字母
[A-Z]   匹配任意一个大写字母
[0-9]   匹配任意一个数字，类似[0123456789]
[0-9a-zA-Z]     匹配任意的数字和字母
[0-9a-zA-Z_]    匹配任意的数字,字母和下划线
[^qyx]  匹配除了qyx这几个字母以外的所有字符，中括号里的^称为脱字符，表示不匹配集合中的字符
[^0-9]  匹配所有的非数字字符
\d    匹配数字，效果同[0-9]
\D    匹配非数字字符，效果同[^0-9]
\w    匹配数字，字母，下划线，效果同[0-9a-zA-Z_z]
\W    匹配非数字，字母，下划线，效果同[^0-9a-zA-Z_]
\s    匹配任意的空白符(空格，换行，回车，换页，制表)，效果同[ \r\n\r\t]
\S    匹配任意的非空白符，效果同[^ \r\n\r\t]

'''
print(re.search(".", "qyx is a good man"))
print(re.search("[0123456789]", "qyx is a good man6"))

print("--------锚字符(边界字符)--------")
r'''
^    行首匹配，和在[]里的^不是一个意思
$    行尾匹配
\A   匹配字符串开始，它和^的区别是 \A只匹配整个字符串的开头，即使在re.M模式下也不会匹配其它行的行首
\Z   匹配字符串结束，它和$的区别是 \Z只匹配整个字符串的结束，即使在re.M模式下也不会匹配其它行的行尾
\b   匹配一个单词的边界，也就是指单词和空格间的位置  
\B   匹配非单词边界

'''
print(re.search("^qyx", "qyx is a good man"))
print(re.search("qyx$", "qyx is a good man qyx"))
print(re.findall("\Aqyx", "qyx is a good man qyx\nqyx is a good man", flags=re.M))
print(re.findall("^qyx", "qyx is a good man qyx\nqyx is a good man", flags=re.M))

print(re.search(r"er\b", "never"))
print(re.search(r"er\b", "nerve"))
print(re.search(r"er\B", "never"))
print(re.search(r"er\B", "nerve"))

print("------------------匹配多个字符---------------------")

'''
说明：下方的x,y,z均为假设的普通字符，不是正则表达式的元字符
(xyz)   匹配小括号内的xyz(作为一个整体去匹配)
x?      匹配0个或者1个x
x*      匹配0个或者任意多个x  (.* 表示匹配0个或者任意多个字符(换行符除外))
x+      匹配至少一个x
x{n}    匹配确定的n个x(n是一个非负整数)
x{n,}   匹配至少n个x
x{n,m}  匹配至少n个最多m个x,注意：n<=m
x|y     表示或，匹配的是x或y

*?    +?    x?   最小匹配,通常都是尽可能多的匹配，可以使用这种方式解决贪婪匹配
(?:x)   类似(xyz)，但不表示一个组
'''
print(re.findall(r"(qyx)", "qyxgood is a good man,qyx is a nice man"))
print(re.findall(r"((q|Q)yx)", "qyx-----Qyx"))

str1 = "qyx is a good man!qyx is a nice man!qyx is a very handsome man"
print(re.findall(r"qyx.*?man", str1))

# 注释：/* part1 */   /* part2 */
print(re.findall(r"//*.*?/*/", r"/* part1 */   /* part2 */"))


# 判断手机号码

pat = r"^1(([3578]\d)|(47))\d{8}$"
str1p = "jsdkfjkdksdkkk13377996910ssssjjjj15911298480slaalallala17853511734"
str2 = "17853511734"
print(re.match(pat, str2))

# re 模块深入
'''
字符串切割
re.split函数
'''

str6 = "qyx is                     a  good man"
print(re.split(r" +", str6))

'''
re.finditer(pattern,string,flags=0)函数
功能:与findall类似，扫描整个字符串，返回的是一个迭代器
'''

str11 = "qyx is a good man! qyx is a nice man! qyx is a handsome man"
d = re.finditer(r"(qyx)", str11)

while True:
    try:
        l = next(d)
        print(l)

    except StopIteration as e:
        break

'''
字符串的替换和修改
re.sub(pattern,repl,string,count=0,flags=0)
re.subn(pattern,repl,string,count=0,flags=0)
pattrern:   正则表达式(规则)
repl:       指定的用来替换的字符串
string:     目标字符串
count:      最多替换次数
flags:      。。。
功能：在目标字符串中以正则表达式的规则匹配字符串,再把他们替换成指定的字符串。
可以指定替换的次数，如果不指定，替换所有的匹配字符串。

区别：前者返回一个被替换的字符串，后者返回一个元组tuple，第一个元素是被替换的字符串，第二个元素是被替换的次数

'''
strth = "qyx is a good good good man"
print(re.sub(r"(good)", "nice", strth))
print(type(re.sub(r"(good)", "nice", strth)))

print(re.subn(r"(good)", "nice", strth))
print(type(re.subn(r"(good)", "nice", strth)))


'''
分组：
概念：除了简单的判断是否匹配之外，正则表达式还有提取子串的功能。用()表示的就是提取分组

'''
strtf = "010-53247654"
m = re.match(r"((?P<second>\d{3})-(\d{8}))", strtf)
print(m)
# 使用序号获取对应组的信息，group(0)代表的是原始字符串
print(m.group(0))
print(m.group(1))
print(m.group(2))
print(m.group(3))
# 查看匹配的各组的情况
print(m.groups())
# ?P<second> 给分组取名字，方便获取到对应名字的内容
print(m.group("second"))


'''
编译：当我们使用正则表达式时，re模块会干两件事
1.编译正则表达式，如果正则表达式本身不合法，会报错
2.用编译后的正则表达式去匹配对象
compile(pattern,flags=0)
'''
# 编译成正则对象
pat = r"^1(([3578]\d)|(47))\d{8}$"
re_telephone = re.compile(pat)

print(re_telephone.match("17853511734"))




