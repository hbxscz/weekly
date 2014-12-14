<%--
  Created by IntelliJ IDEA.
  User: figo
  Date: 14/12/11
  Time: 下午9:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script type="text/javascript"  src="${ctx}/static/highcharts/highcharts.js"></script>
    <script type="text/javascript">
        $(function() {
            $('#container')
                    .highcharts(
                    {
                        title : {
                            text : 'Daily Sells Information',
                            x : -20
                            //center
                        },
                        subtitle : {
                            text : 'source:www.ebay.com',
                            x : -20
                        },
                        xAxis : {
                            categories : []
                        },
                        yAxis : {
                            title : {
                                text : 'sales qty'
                            },
                            plotLines : [ {
                                value : 0,
                                width : 1,
                                color : '#808080'
                            } ]
                        },
                        tooltip : {
                            valueSuffix : ''
                        },
                        legend : {
                            layout : 'vertical',
                            align : 'right',
                            verticalAlign : 'middle',
                            borderWidth : 0
                        },
                        series : [{
                            name: 'Qty',
                            data: ${data}
                        }]
                    });
        });
    </script>
</head>
<body>
<div id="container"
     style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>
