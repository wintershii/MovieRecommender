function analytics(){
    
    var info;
    info = getInfo();
    batchSend(info);

    // fetch(
    //     "http://localhost:80/upload.gif?uid=ssfsf-42221&date="+Date.now(),
    //     {method:"head"}
    // )
}

//数据上报
function send(uploadData){
    //数据上报
    //image对象发送上报请求
    //   var image = new Image(1,1)
    //   image.src = "http://localhost:80/upload.gif?uid=ssfsf-42221&date="+Date.now()
    //fetch()发送上报请求
    var _data = Base64.encode(uploadData);
    var url = "http://localhost:80/upload.gif?"+ _data;
    fetch(
        url,
        {method:"head",mode:"no-cors"}
    )
}

//批量发送
function batchSend(infoData){


    var num = 3;
    var u_key = "u_data";
    var obj;
    var key = localStorage.getItem(u_key);
    if(key == null){
        obj = []
    }else{
        obj = JSON.parse(key);
    }

    if(obj.length >= num){
        send(JSON.stringify(obj));
        localStorage.removeItem(u_key);
    }else{
        obj.push(infoData);
        localStorage.setItem(u_key,JSON.stringify(obj));
    }
}

//基础数据组装
function basicInfo(){

    var basicData = {};
    var uuid = null;
    var agent = null;

    uuid = getUUID();
    agent = getAgent();

    basicData['uuid'] = uuid;
    basicData['agent'] = agent;
    basicData['time'] = Date.now();

    return basicData;

}

//获取用户uid
function getUUID(){

    var uid = null;
    var c_key = "uuid"; 
    var start = document.cookie.indexOf(c_key+"=");
    if(start != -1){
        start = start + c_key.length + 1;
        var end = document.cookie.length;
        uid = unescape(document.cookie.substring(start,end));
    }

    return uid;

}

//获取客户端的操作系统
function getAgent(){

    var agent = null;
    var userAgent = navigator.userAgent;
    var agents =  ["Android", "iPhone","iPad"];
    var flag = true;
    for(var i=0;i<agents.length;i++){
        if(userAgent.indexOf(agents[i]) > 0){
            agent = agents[i];
            flag = false;
            break;
        }
    }

    if(flag){
        agent = "windows";
    }

    return agent;

}

//数据组装
function getInfo(extendData = null){

    var data;
    data = basicInfo();
    data['event'] = extendData;

    return data;

}

export {getInfo,batchSend,analytics}

