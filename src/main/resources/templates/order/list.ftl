
<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
    <#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">

        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                <thead>
                <tr>
                    <th>订单编号                    </th>
                    <th>                        姓名                    </th>
                    <th>                        手机号                    </th>
                    <th>                        地址                    </th>
                    <th>                        订单金额                    </th>
                    <th>                        订单状态                    </th>
                    <th>                        支付状态                    </th>
                    <th>                        创建时间                    </th>
                    <th colspan="2">                        操作                    </th>
                </tr>
                </thead>
                <tbody>

<#list orderDTOPage.getContent() as orderDTO>
                <tr>
                    <td>                        ${orderDTO.orderId}                    </td>
                    <td>                        ${orderDTO.buyerName}</td>
                    <td>                        ${orderDTO.buyerPhone}                    </td>
                    <td>                        ${orderDTO.buyerAddress}                    </td>
                    <td>                        ${orderDTO.orderAmount}                    </td>
                    <td>                        ${orderDTO.getOrderStatusEnum().getMessage()}                    </td>
                    <td>                        ${orderDTO.getPayStatusEnum().getMessage()}                    </td>
                    <td>                        ${orderDTO.createTime}                    </td>
                    <td>                       详情                    </td>
                    <td>
                        <#if orderDTO.getOrderStatusEnum().getMessage()!="已取消" >
<a href="/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                        </#if>

                    </td>
                </tr>
</#list>

                </tbody>
            </table>


        </div>

        <div class="row clearfix">
        <div class="col-md-12 column">
            <ul class="pagination">
                <#if currpage lte 1>
                <li class="disabled"><a href="#">上一页</a> </li>
                    <#else>
                  <li><a href="/seller/order/list?page=${currpage-1}&size=1">上一页</a> </li>
                </#if>
<#list 1..orderDTOPage.getTotalPages() as index>
<#if currpage== index>
                <li class="disabled"><a href="/seller/order/list?page=${index}&size=1">${index}</a></li>
<#else>
 <li><a href="/seller/order/list?page=${index}&size=1">${index}</a></li>
</#if>
</#list>
                 <#if currpage gte orderDTOPage.getTotalPages()>
                <li class="disabled"><a href="#">下一页</a> </li>
                 <#else>
                  <li><a href="/seller/order/list?page=${currpage+1}&size=1">下一页</a> </li>
                 </#if>
            </ul>
        </div>
        </div>


    </div>
</div>
</div>
</div>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>
    setTimeout('location.href="${url}"',3000);
</script>
</body>


</html>
