CREATE TABLE movie_description (
    id INT(8) NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    score varchar(5) NOT NULL,
    peoples varchar(10) NOT NULL,
    describ varchar(255) NOT NULL,
    pic varchar(255) NOT NULL,
    PRIMARY KEY(id)
)
insert into movie_description values(1,'1','2','22','23','233');

CREATE TABLE movie_users (
    id INT(8) NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    password varchar(100) NOT NULL,
    age INT(3) NOT NULL,
    gender TINYINT NOT NULL,
    PRIMARY KEY(id)
)

CREATE TABLE movie_details (
    id INT(8) NOT NULL AUTO_INCREMENT,
    m_id INT(8) NOT NULL,
    director varchar(100) NOT NULL,
    actor varchar(100) NOT NULL,
    plot text NOT NULL,
    comments text NOT NULL,
    PRIMARY KEY(id),
    UNIQUE KEY(m_id)
)

CREATE TABLE movie_scores (
    id INT(8) NOT NULL AUTO_INCREMENT,
    u_id INT(8) NOT NULL,
    m_id INT(8) NOT NULL,
    score INT(2) NOT NULL,
    PRIMARY KEY(id)
)
ALTER table movie_scores add INDEX `u_m_index`(`u_id`, `m_id`);

CREATE TABLE movie_collect (
    id INT(8) NOT NULL AUTO_INCREMENT,
    u_id INT(8) NOT NULL,
    m_id INT(8) NOT NULL,
    deleted TINYINT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    INDEX u_index(u_id)
)


//109
INSERT INTO movie_details (m_id, director, actor, plot, comments) VALUES(110, '大卫·芬奇', 
    '本·阿弗莱克 / 裴淳华 / 尼尔·帕特里克·哈里斯', 
    '结婚五周年纪念日的早上，尼克·邓恩（本·阿弗莱克 Ben Affleck 饰）来到妹妹玛戈（凯莉·库恩 Carrie Coon 饰）的酒吧，咒骂抱怨那个曾经彼此恩爱缠绵的妻子艾米（罗莎蒙德·派克 Rosamund Pike 饰）以及全然看不见希望的婚姻。当他返回家中时， 却发现客厅留下了暴行的痕迹，而妻子竟不见了踪影。女探员朗达·邦妮（金·迪肯斯 Kim Dickens 饰）接到报案后赶来调查，而现场留下的种种蛛丝马迹似乎昭示着这并非是一件寻常的失踪案，其背后或许隐藏着裂变于夫妻之情的谋杀罪行。艾米的失踪通过媒体大肆渲染和妄加揣测很快闻名全国，品行不端的尼克被推上风口浪尖，至今不见踪影的爱人对他进行无情审判，你侬我侬的甜言蜜语早已化作以血洗血的复仇与折磨……
　　本片根据吉莉安·弗琳（Gillian Flynn）的同名小说改编。', 
    '相信我...其实，这就是马伊琍和文章的故事...\tI love David Fincher! P.S.: 开始前，旁边坐在一群couples中的单身小哥无聊郁闷地喝咖啡；结束后，他幸灾乐祸地跟我说，Now every couple feels no good...\t这片无敌了。故事太尼玛牛了。人物状态看得我蛋都疼了。芬奇就是我的神！\t讲了一个天蝎腹黑绿茶婊的故事！\t奥斯卡最佳改编剧本和最佳女主角的有力竞争者，当然这也绝对是一部芬奇风格的佳作。这是一个从头到尾都猜不中接下来会发生什么情节的故事，叙事视角转换，真相谎言交织，边看只能边感概中国再过一百年也写不出这水平，回来一查原著小说曾经连续八周位居纽约时报精装小说畅销排行榜第一名。【MCL JP】\t')

//155
INSERT INTO movie_details (m_id, director, actor, plot, comments) VALUES(156, '汉内斯·赫尔姆', '罗夫·拉斯加德 / 巴哈·帕斯 / 托比亚斯·阿姆博瑞', 
    '欧维（罗夫·拉斯加德 Rolf Lassgård 饰）是一个刻板而又固执的老头，他的妻子半年前死于疾病，留他一人生活在这个混乱不堪的世界之中。每天早晨，欧维都会定时在社区里进行巡视，确认所有的车辆都停在应停的位置，呵斥违反规定私自驶入社区的车辆，赶走四处乱转破坏环境的猫狗，在社区居民眼里，欧维是“来自地狱的恶邻”，可每个人都明白，这其实是欧维对于社区爱之深刻的表现。
　　某一日，欧维遭到了上司的解雇，离开了恪守了几十年的工作岗位，心灰意冷对现世了无牵挂的欧维决定自杀。然而，就在这个节骨眼上，一位名为帕维娜（巴哈·帕斯 Bahar Pars 饰）的女子和丈夫带着两个孩子搬到了欧维的隔壁，成为了欧维自杀计划的绊脚石。', 
    '气哼哼的人要是爱起这个世界来，真是一把鼻涕一把泪啊。\t忧郁蓝为构色基调，适当的黑色幽默略显温馨，亡妻回忆杀构建出老人高大的男友力max形象，毒舌暴戾的外衣下是颗极感性的心，所有的抱怨均是对这片土地爱的深沉，古板和偏执让人格更显真实。当然，作为北欧片少不了提下环境与人文问题。北欧的天和一个一生爱你的男人，这就是女人一生想要的了吧。\t越多白痴的声音议论她，我对她仅存的回忆就越少。\t笑了哭哭了笑。人老了真的很容易变得“讨人嫌”：我奶奶八十多岁有一次不小心滑倒骨折，之后她每天都在说，我为什么还不死，活着简直就在给大家添麻烦。那时候我回去看她，被她念得头疼。现在她九十好几，以为我还只有十五六岁，她好像记不太清年月，但有时候我又觉得，她其实什么都清楚。\t让人想得到美好过往，又想象得出失去所爱的愧疚与遗恨。寻常情节，但在悠扬乐韵浸润下，Parvaneh对Ove生活的介入，总显得特别舒适美好。她太可爱，聒噪霸道却阳光灿烂，有些坚冰，只能由这类人消融。太像相伴Ove一生的Sonja，让人相信命运在悲剧后会作出奇异弥补。而当明光重现，静候感恩便是。\t')


