/*
* * 注意：
* * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
* * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
* * 3. 完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html    *
* * 如有问题请通过以下渠道反馈：
* * 邮箱地址：weixin-open@qq.com
* * 邮件主题：【微信JS-SDK反馈】具体问题
* * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
* */

wx.config({
    debug: true,
    appId: 'wxb67cf3f14d6861e4',
    timestamp: 1441870958,
    nonceStr: 'bb238f2d-f654-44c5-a8a9-67407d29e301',
    signature: '3c708bc428ec48f96485d89c85cffbe0bde9a6be',
    jsApiList: [
        'onMenuShareTimeline'
    ]
});
/*
 *  * 注意：
 *  * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
 *  * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
 *  * 3. 完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html  *
 *  * 如有问题请通过以下渠道反馈：
 *  * 邮箱地址：weixin-open@qq.com
 *  * 邮件主题：【微信JS-SDK反馈】具体问题
 *  * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
 *  */
wx.ready(function () {
    wx.onMenuShareTimeline({
        title: '互联网之子',
        link: 'http://movie.douban.com/subject/25785114/',
        imgUrl: 'http://img3.douban.com/app.guangda.view/movie_poster_cover/spst/public/p2166127561.jpg',
        trigger: function (res) {
            alert('用户点击分享到朋友圈');
        },
        success: function (res) {
            alert('已分享');
        },
        cancel: function (res) {
            alert('已取消');
        },
        fail: function (res) {
            alert(JSON.stringify(res));
        }
    });
})