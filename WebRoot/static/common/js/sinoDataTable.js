/**
 * Created by huangqiang on 16/12/25.
 * TODO:可筛选的表格
 * 依赖Jquery
 */
;(function(root, doc){
    var dataTable = function($dom, options) {
        var conf; // 默认参数

        conf = {
            dataAlias     : "result",
            isInit        : true,         // 是否初化
            isScrollTable : false,        // 是否带滚动的表格
            pageSize      : 5,            // 显示的页面数
            recordSize    : [10, 25, 30], // 每页记录数列表
            curRecordSize : 10,           // 默认记录数
            postType      : "post",       // 请求方式
            scrollTableSty: "sino-scroll-table"
        };

        // 扩展参数
        if(typeof options === "object") {
            this.opts = $.extend({}, conf, options);
        } else {
            this.opts = $.extend({}, conf);
        }


        this.$dom = $dom;
        this.curPage = 1;
        this.totalPage = 1;
        this.loading = false;
        // 组件内发送的数据
        this.localPostData = {};
        // 样式选择器
        this.cssSelector = {
            layoutWrap     : ".js-layout-wrap",
            mainTableWrap : ".js-main-table-wrap",
            opTableWrap   : ".js-aside-table-wrap",
            mainTable : ".js-main-table",   // 主表格样式选择器
            opTable   : ".js-aside-table",  // 副表格样式选择器
            tBody     : ".js-tbody-data",   // 两个表格相同样式选择器
            page      : ".js-sino-page",    // 页面样式选择器
            pageNum   : ".sino-page",
            prevPage  : ".prev",
            nextPage  : ".next",
            record    : ".js-record",
            bySort    : ".js-sort"
        };
        // 保存排序方式
        this.sortData = {
            sortType: ["asc", "desc"]       // asc升序, desc降序
        };

        if(this.$dom && !!this.$dom.length && this.opts.isInit) {
            this.init();
        }

        return this;

    };

    dataTable.prototype = {
        init: function() {
            this.loadData();
            if(this.opts.isScrollTable) {
                this.resizeEvent();
            }
            this.pageEvent();
            this.sortEvent();
            this.opts.isInit = true;
        },
        // 设置查询信息
        setSearch: function(objFilter) {
            if(typeof objFilter === "object" && !this.loading) {
                this.search = objFilter;

                // 搜索时页面重置为1
                this.curPage = 1;

                // 如果已经初化则更新数据
                if(this.opts.isInit) {
                    this.updateData();
                } else {
                    this.init();
                }

            }
        },
        loadData: function() {
            var opts = this.opts
            ,   url = opts.url
            ,   self = this
            ,   postData
            ;

            /**
             * 参数设置
             * PageSize : 每页条数
             * pageNo: 当前页
             * sortCol: 排序字段名
             * sortType: 排序类型
             * 筛选条件
             */

            if(this.loading) {
                return ;
            }

            this.loading = true;
            // 设置发送参数
            this.setLocalPostData();
            postData = this.getPostData();

            $[opts.postType](url, postData, function(data){
                var data = typeof data == "string" ? $.parseJSON(data) : data;
                self.renderLayout();
                self.renderTable();
                self.renderTbody(data);
                self.setTotalPage(data.iTotalRecords);
                self.renderPage();
                self.recordEvent();
                self.loading = false;
                if(opts.isScrollTable) {
                    self.resetSize();
                }
            }, "json");
        },
        updateData: function() {
            var opts = this.opts
            ,   url = opts.url
            ,   self = this
            ,   postData
            ;

            if(this.loading) {
                return ;
            }
            this.loading = true;
            this.setLocalPostData();
            postData = this.getPostData();

            $[opts.postType](url, postData, function(data){
                var data = typeof data == "string" ? $.parseJSON(data) : data;
                self.renderTbody(data);
                self.setTotalPage(data.iTotalRecords);
                self.renderPage();
                self.loading = false;
            }, "json");
        },
        // 获取发送请求的数据
        getPostData: function() {
            var postData;
            postData = $.extend({}, this.search, this.localPostData);
            return postData;
        },
        // 设置组件内请求数据
        setLocalPostData: function() {
            var sortCol
            ,   sortType
            ,   sortData = this.sortData;
            ;

            sortCol = sortData.curParam ? sortData.curParam : "";
            sortType = ("index" in sortData ) ? sortData.sortType[sortData.index] : "";

            this.localPostData = {
                PageSize : this.opts.curRecordSize,
                pageNo   : this.curPage,
                sortCol  : sortCol,
                sortType : sortType
            };

        },
        /**
         * 渲染结果
         * @param data
         */
        renderLayout: function() {
            var opts = this.opts
            ,   layoutHtml        // 布局dom
            ,   recordSelect = ''  // 记录数select
            ,   aiseTable         // 不滚动的表格
            ,   selectedFlag
            ;

            recordSelect += '<select class="form-control select-record js-record">';
            for(var i = 0; i < opts.recordSize.length; i++) {
                selectedFlag = opts.recordSize[i] == opts.curRecordSize ? 'selected="selected' : '';
                recordSelect += '<option class="js-record-size" ' + selectedFlag + ' value="' + opts.recordSize[i] + '">';
                recordSelect += opts.recordSize[i];
                recordSelect += '</option>';
            }
            recordSelect += '</select>';

            aiseTable = opts.isScrollTable ? '<div class="aside-table-wrap js-aside-table-wrap"></div>' : '';

            layoutHtml = [
                 '<div class="form-inline tb-captions">'
                ,    '每页显示'
                ,    recordSelect
                ,    '条记录'
                , '</div>'
                , '<div class="layout-wrap js-layout-wrap clearfix">'
                ,    '<div class="main-table-wrap js-main-table-wrap">'
                ,    '</div>'
                ,    aiseTable
                , '</div>'
                , '<div class="sino-page-wrap js-sino-page">'
                , '</div>'
            ].join("");

            this.$dom.html(layoutHtml);
            if(opts.isScrollTable) {
                this.$dom.addClass(opts.scrollTableSty);
            }

        },
        // 排序事件
        sortEvent: function() {
            var opts = this.opts
            ,   cssSelector = this.cssSelector
            ,   self = this
            ,   sortSty0 = self.sortData.sortType[0]
            ,   sortSty1 = self.sortData.sortType[1]
            ;

            this.$dom.on("click", cssSelector.bySort, function(event) {
                var param = $(this).data("sorttype");
                if(typeof self.sortData.curParam != undefined && self.sortData.curParam == param) {
                    self.sortData.index = 1 - self.sortData.index;
                } else {
                    self.sortData.index = 0;
                }

                $(this).siblings().removeClass(sortSty0).removeClass(sortSty1)
                $(this).removeClass(sortSty0)
                       .removeClass(sortSty1)
                       .addClass(self.sortData.sortType[self.sortData.index]);
                self.sortData.curParam = param;
                self.updateData();
            });


        },
        // 记录下拉列表事件
        recordEvent: function() {
            var $dom = this.$dom
            ,   cssSelector = this.cssSelector
            ,   self = this
            ;

            $dom.find(cssSelector.record).on("change", function(){
                self.opts.curRecordSize = parseInt($(this).val());
                // 更改时页码变为1
                self.curPage = 1;
                self.updateData();
            });

        },
        resizeEvent: function() {
            var self = this;
            $(window).resize(function(){
                self.resetSize();
            });
        },
        // 左右自适合
        resetSize: function() {
            var totalWidth = 0
            ,   opts = this.opts
            ,   cssSelector = this.cssSelector
            ,   thLen
            ,   wrapWidth           // 整个区域的宽度
            ,   asideWidth          // 侧边区域的宽度
            ,   $dom = this.$dom
            ;

            thLen = opts.theadParam.length;
            wrapWidth = $dom.width();

            for(var i = 0; i < thLen - 1; i++) {
                totalWidth += parseInt(opts.theadParam[i].width);
            }

            asideWidth = opts.theadParam[thLen - 1].width;

            $dom.find(cssSelector.layoutWrap).css({width: wrapWidth});
            $dom.find(cssSelector.mainTableWrap).css({width: wrapWidth - asideWidth});
            $dom.find(cssSelector.opTableWrap).css({width: asideWidth});
            // 如果可显示区域大于表格宽度
            if(wrapWidth - asideWidth > totalWidth) {
                $dom.find(cssSelector.mainTable).css({width: "100%"});
            } else {
                $dom.find(cssSelector.mainTable).css({width: totalWidth});
            }

        },
        // 渲染表格内容
        renderTbody: function(data) {
            var opts = this.opts
            ,   len =  $.isArray(data[opts.dataAlias]) ? data[opts.dataAlias].length : 0
            ,   tbodyHtml = ""
            ,   cssSelector = this.cssSelector
            ,   asideHtml = ""
            ,   self = this
            ;

            // 如果是滚动表格则列数-1
            tdLen = opts.isScrollTable ? opts.theadParam.length - 1 : opts.theadParam.length;

            for(var i = 0; i < len; i++) {
                var trData = data[opts.dataAlias][i];

                tbodyHtml += '<tr>';
                for(var j = 0; j < tdLen; j++) {
                    if(typeof opts.theadParam[j].paramValue === "function") {
                        tbodyHtml += '<td>' + opts.theadParam[j].paramValue(trData) + '</td>';
                    } else {
                        tbodyHtml += '<td>' + trData[opts.theadParam[j].paramValue] + '</td>';
                    }
                }
                tbodyHtml += '</tr>';

                // 如果带滚动表格
                if(opts.isScrollTable) {
                    asideHtml += '<tr>';
                    asideHtml += '<td>' + opts.theadParam[tdLen].paramValue(trData) + '</td>';
                    asideHtml += '</tr>';
                }
            }


            this.$dom.find(cssSelector.mainTable).find(cssSelector.tBody).html(tbodyHtml);
            // 如果带滚动表格
            if(opts.isScrollTable) {
                this.$dom.find(cssSelector.opTable).find(cssSelector.tBody).html(asideHtml);
                setTimeout(function(){
                    self.resetTdHeight.call(self);
                }, 10);
            }

        },
        // 渲染表格框架
        renderTable: function(){
            var opts = this.opts
            ,   theadHtml
            ,   thList = []
            ,   tableHtml
            ,   cssSelector = this.cssSelector
            ,   asideTableHtml
            ,   sortType
            ,   sortStyle    // 表头样式
            ,   isFirstSort = true  // 是否是第一个排序字段
            ;

            thLen = opts.isScrollTable ? opts.theadParam.length - 1 : opts.theadParam.length;

            for(var i = 0; i < thLen; i++) {
                if(typeof opts.theadParam[i].paramValue === "function" && !opts.theadParam[i].isSort) {
                    sortType = "";
                } else {
                    sortType = 'data-sorttype="' + opts.theadParam[i].fieldName + '"';
                }

                if(isFirstSort && opts.theadParam[i].isSort) {
                    sortStyle = 'class="js-sort th-sort desc" ';
                    isFirstSort = false;
                } else if(opts.theadParam[i].isSort) {
                    sortStyle = 'class="js-sort th-sort" ';
                } else {
                    sortStyle = '';
                }

                thList.push('<th ' + sortStyle + sortType + '" width="' + opts.theadParam[i].width + '"> ' + opts.theadParam[i].paramDesc + '</th>');
            }

            theadHtml = [
                '<tr>'
                ,    thList.join("")
                , '</tr>'
            ].join("");

            tableHtml = [
                 '<table class="table table-bordered js-main-table">'
                ,    '<thead>'
                ,       theadHtml
                ,    '</thead>'
                ,    '<tbody class="js-tbody-data">'
                ,    '</tbody>'
                , '</table>'
            ].join("");

            if(opts.isScrollTable) {
                asideTableHtml = [
                      '<table class="table table-bordered js-aside-table">'
                    ,    '<thead>'
                    ,       '<th>'
                    ,           opts.theadParam[thLen].paramDesc
                    ,       '</th>'
                    ,    '</thead>'
                    ,    '<tbody class="js-tbody-data">'
                    ,    '</tbody>'
                    , '</table>'
                ].join("");
                this.$dom.find(cssSelector.opTableWrap).html(asideTableHtml);
            }

            this.$dom.find(cssSelector.mainTableWrap).html(tableHtml);

        },
        // 重置高度
        resetTdHeight: function() {
            var opts = this.opts
            ,   cssSelector = this.cssSelector
            ,   self = this
            ,   eleTag = "tr"
            ;

            this.$dom.find(cssSelector.mainTable).find(cssSelector.tBody).find(eleTag).each(function (index) {
                self.$dom.find(cssSelector.opTable).find(cssSelector.tBody).find(eleTag).eq(index).css({
                    height: $(this).height()
                });
            });

        },
        setTotalPage: function(totalRecords) {
            var opts = this.opts;

            this.totalPage = Math.ceil(totalRecords / opts.curRecordSize);

        },
        /**
         * 获取页面数组
         * @param curPage   : 当前页
         * @param totalPage : 总页数
         * @param pageSize  : 显示几个页码
         * @returns {Array}
         */
        getPages: function(curPage, totalPage, pageSize){
            var pageStart
            ,	pageEnd
            ,	newPages=[]
            ;

            pageStart = curPage - (pageSize - 1) / 2;

            if(pageStart < 1) {
                pageStart = 1;
            }
            pageEnd = pageStart + pageSize - 1;

            if(pageEnd > totalPage){
                pageStart = (totalPage - pageSize) > 0 ? totalPage - pageSize : 1;
                pageEnd = totalPage;
            }


            for(var i=pageStart; i<pageEnd+1; i++) {
                newPages.push(i);
            }

            return newPages;

        },
        /**
         * 渲染页码
         */
        renderPage: function() {
            var pages
            ,   opts = this.opts
            ,   pageHtml = ''
            ,   cssSelector = this.cssSelector
            ,   prevSty
            ,   nextSty
            ;

            pages = this.getPages(this.curPage, this.totalPage, opts.pageSize);

            prevSty = this.curPage == 1 ? 'class="prev disabled"' : 'class="prev"';
            nextSty = this.curPage == this.totalPage ? 'class="next disabled"' : 'class="next"';

            pageHtml += '<a ' + prevSty + ' href="javascript:void(0)">上一页</a>';
            for(var i = 0, len = pages.length; i < len; i++) {
                if(i == 0 && pages[i] > 2) {
                    pageHtml += '<a href="javascript:void(0)" class="sino-page">1</a>';
                    pageHtml += '<span>...</span>';
                }

                if(i == 0 && pages[i] == 2) {
                    pageHtml += '<a href="javascript:void(0)" class="sino-page">1</a>';
                }

                if(this.curPage == pages[i]) {
                    pageHtml += '<a href="javascript:void(0)" class="sino-page current">' + pages[i] + '</a>';
                } else {
                    pageHtml += '<a href="javascript:void(0)" class="sino-page">' + pages[i] + '</a>';
                }

                if(i == pages.length - 1 && this.totalPage > pages[i] + 1) {
                    pageHtml += '<span>...</span>';
                    pageHtml += '<a href="javascript:void(0)" class="sino-page">' + this.totalPage + '</a>'
                }
                if(i == pages.length - 1 && this.totalPage == pages[i] + 1) {
                    pageHtml += '<a href="javascript:void(0)" class="sino-page">' + this.totalPage + '</a>'
                }
            }

            pageHtml += '<a ' + nextSty + ' href="javascript:void(0)">下一页</a'

            this.$dom.find(cssSelector.page).html(pageHtml);
        },
        // 选择页码
        selectPage: function($ele) {
            this.curPage = $.trim($ele.text());
            this.updateData();
        },
        // 上一页
        prevPage: function() {
            if(this.curPage > 1) {
                this.curPage--;
                this.updateData();
            }
        },
        // 下一页
        nextPage: function() {
            if(this.curPage < this.totalPage) {
                this.curPage++;
                this.updateData();
            }
        },
        // 页码相关事件
        pageEvent: function() {
            var $dom = this.$dom
            ,   self = this
            ,   cssSelector = this.cssSelector
            ;

            // 点击页事件
            $dom.on("click", cssSelector.pageNum, function(){
                self.selectPage($(this));
            });

            // 上一页
            $dom.on("click", cssSelector.prevPage, function(){
                self.prevPage()
            });

            // 下一页
            $dom.on("click", cssSelector.nextPage, function(){
                self.nextPage();
            });

        }
    };

    root.sinoDataTable = function($dom, options) {
        return new dataTable($dom, options);
    }

})(window, document);