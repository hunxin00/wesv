<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="Content-Style-Type" content="text/css"/>
    <title></title>
    <style type="text/css">
        body {
            font-family: SimSun;
            padding: 0px;
            margin: 0px;
        }

        .center {
            text-align: center;
            width: 80%;
            margin: 0 auto;
        }

        .indexTitle {
            height: 33px;
            color: rgba(0, 0, 0, 0.85);
            font-size: 40px;
            text-align: center;
            padding-top: 236px;
        }

        .page {
            width: 690px;
            height: 1010px;
            /* width: 100%;
            height: 950px; */
            text-align: center;
            position: relative;
        }

        .indexFooter {
            width: 100%;
            height: 60px;
            text-align: center;
            position: absolute;
            bottom: 212px;
            left: 0px;
        }

        .page2 {
            width: 690px;
            padding: 40px 20px;
            box-sizing: border-box;
        }

        .page2 p {
            table-layout: fixed;
            word-wrap: break-word;
            word-break: break-all;
        }

        .contanierFirstTitle {
            height: 20px;
            color: #333;
            font-size: 18px;
            text-align: left;
        }

        .grayDescptionBox {
            text-align: left;
            width: 100%;
            padding: 20px;
            box-sizing: border-box;
            display: flex;
            padding-left: 25px;
        }

        .grayDescptionLable {
            width: 60px;
            margin-right: 10px;
            opacity: 0.65;
            color: rgba(0, 0, 0, 0.85);
            font-size: 12px;
            text-align: left;
        }

        .grayDescptionTitle {
            opacity: 0.85;
            color: #333;
            font-size: 12px;
            text-align: left;
            flex: 1;
            overflow-wrap: break-word;
            word-break: break-word;
        }

        .contanierSencondTitle {
            height: 17px;
            opacity: 0.85;
            color: #666;
            font-size: 16px;
            text-align: left;
        }

        .grayDescptionTwoBox {
            text-align: left;
            width: 100%;
            padding: 20px;
            box-sizing: border-box;
            padding-left: 25px;
        }

        .halfWidthBox {
            width: 50%;
            float: left;
        }

        .halfWidthLableBox {
            width: 120px;
            margin-right: 10px;
            opacity: 0.65;
            color: rgba(0, 0, 0, 0.85);
            font-size: 14px;
            text-align: right;
            float: left;
        }

        .halfWidthTitleBox {
            width: 180px;
            opacity: 0.65;
            color: rgba(0, 0, 0, 0.85);
            font-size: 14px;
            text-align: left;
            float: left;
        }

        .lineBox {
            clear: both;
            height: 1px;
            border-top: 1px solid #D8D8D8;
            width: 100%;
        }

        .cl {
            clear: both;
        }

        /* .forEditer p{
        }   */
        .forEditer img {
            max-width: 100%;
            margin: 10px auto;
        }

        .tl_talbe {
            border: 1px #e6e3e3;
        }
    </style>
</head>
<body>
<div class="page">
    <table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
        <tbody>
        <tr>
            <td class="indexTitle">${docTitle}</td>
        </tr>
        <tr>
            <td style="padding-top: 400px;text-align: center; font-size: 22px;">${doccontent}</td>
        </tr>
        <tr>
            <td style="text-align: center; padding-top: 25px; font-size: 20px;">${createTime}</td>
        </tr>
        </tbody>
    </table>
</div>

</body>
</html>