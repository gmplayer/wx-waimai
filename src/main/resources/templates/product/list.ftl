
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
                    <th>商品ID                    </th>
                    <th>                        名称                    </th>
                    <th>                        图片                    </th>
                    <th>                        单价                    </th>
                    <th>                        库存                    </th>
                    <th>                        描述                    </th>
                    <th>                        类目                    </th>
                    <th>                        创建时间                    </th>
                    <th>                        修改时间                    </th>
                    <th colspan="2">                        操作                    </th>
                </tr>
                </thead>
                <tbody>

<#list productInfoPage.getContent() as productInfo>
                <tr>
                    <td>                        ${productInfo.productId}                    </td>
                    <td>                        ${productInfo.productName}</td>
                    <td>   <img src="${productInfo.productIcon}" height="100" width="100" />                                       </td>
                    <td>                        ${productInfo.productPrice}                    </td>
                    <td>                        ${productInfo.productStock}                    </td>
                    <td>                        ${productInfo.productDescription}                    </td>
                    <td>                        ${productInfo.categoryType}                    </td>
                    <td>                        ${productInfo.createTime}                    </td>
                    <td>                        ${productInfo.updateTime}                    </td>
                    <td>      <a href="/seller/product/index?productId=${productInfo.productId}"  >     修改   </a>                 </td>
                    <td>
<#if productInfo.getProductStatusEnum().message=="上架">
    <a href="/seller/product/off_sale?productId=${productInfo.productId}"  >  下架</a>
<#else>
<a href="/seller/product/on_sale?productId=${productInfo.productId}"  >  上架</a>
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
                <#if currpage lte 0>
                <li class="disabled"><a href="#">上一页</a> </li>
                    <#else>
                  <li><a href="/seller/product/list?page=${currpage-1}&size=1">上一页</a> </li>
                </#if>
<#list 1..productInfoPage.getTotalPages() as index>
<#if currpage== index-1>
                <li class="disabled"><a href="/seller/product/list?page=${index}&size=1">${index}</a></li>
<#else>
 <li><a href="/seller/product/list?page=${index}&size=1">${index}</a></li>
</#if>
</#list>
                 <#if currpage gte productInfoPage.getTotalPages()-1>
                <li class="disabled"><a href="#">下一页</a> </li>
                 <#else>
                  <li><a href="/seller/product/list?page=${currpage+1}&size=1">下一页</a> </li>
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
