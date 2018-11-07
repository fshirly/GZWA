$(document).ready(function() {
	openDescr();
	doInitTable();
	$('#divPenDialog').window({
		minimizable:false,
		maximizable : false,
		collapsible : false,
		resizable:false,
		closed:true,
	    width:650,
	    height:450,	 
	    modal:true
	});
	$('#divCategoryDialog').window({
		minimizable:false,
		maximizable : false,
		collapsible : false,
		resizable:false,
		closed:true,
	    width:650,
	    height:450,	 
	    modal:true
	});
    $('#divMOClassIDDialog').window({
        minimizable:false,
        maximizable : false,
        collapsible : false,
        resizable:false,
        closed:true,
        width:650,
        height:450,
        modal:true
    });
}
);

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	openDescr();
	var path = getRootName();
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/platform/snmpDevice/listSnmpDevice',
						remoteSort : true,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},						
						idField : 'id',
						singleSelect : false,// 是否单选
						//checkOnSelect : false,
						//selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '添加',
							'iconCls' : 'icon-add',
							handler : function() {
								toAdd();
							}
						}, {
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						} ],
						columns : [ [
						         {
						        	 field : 'id',
						        	 checkbox : true
						         }, 
								 {
									field : 'deviceOID',
									title : '设备OID',
									width : 150,
									align : 'left',
									sortable : true
								},
								{
									field : 'deviceModelName',
									title : '设备型号',
									width : 150,
									align : 'center',									
									sortable : true
								},
								{
									field : 'deviceType',
									title : '设备类型',
									width : 80,
									align : 'center',									
									sortable : true
								},
								{
									field : 'deviceNameAbbr',
									title : '设备备注',
									width : 80,
									align : 'center',
									sortable : true
								},
								
								{
									field : 'os',
									title : '操作系统',
									width : 80,
									align : 'center',
									sortable : true
								},

								{
									field : 'deviceIcon',
									title : '设备图标',
									width : 150,
									align : 'center',									
									sortable : true
								},
							{
								field : 'resCategoryName',
								title : '对应资源类型',
								width : 150,
								align : 'center',
								sortable : true
							},
								{
									field : 'ids',
									title : '操作',
									width : 65,
									align : 'center',
									formatter : function(value, row, index) {
										return  '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" />&nbsp;<a style="cursor: pointer;" title="修改" onclick="javascript:toUpdate('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a>&nbsp;<a style="cursor: pointer;" title="删除" onclick="javascript:doDel('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								} ] ]
					});
	 $(window).resize(function() {
	        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
	    });
}

/*
 * 删除任务 
 */
function doDel(id) {	
	$.messager.confirm("操作提示", "您确定删除此信息吗？", function (data) {
        if (data) {
        	var path = getRootName();
			var uri = path + "/platform/snmpDevice/delSnmpDevice";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"id" : id,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
//						$.messager.alert("提示", "信息删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "信息删除失败！", "error");
					}
				}
			}
			ajax_(ajax_param);
        }            
    });	
	
}

/**
 * 打开新增div
 */
function toAdd() {
	$('#divView').dialog('close');
	resetForm('tblDetailInfo');
	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doAdd();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		window.location.href = window.location.href;
		$('#divAddEdit').dialog('close');
	});
	$('#divAddEdit').dialog('open');
}

/**
 * 新增处理
 */
function doAdd(){
	if (checkForm()) {
		var path=getRootName();
		var uri=path+"/platform/snmpDevice/addSnmpDevice";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"pen" : $("#ipt_pen").val(),
				"deviceOID" : $("#ipt_deviceOID").val(),
				"deviceModelName" : $("#ipt_deviceModelName").val(),
				"deviceType" : $("#ipt_deviceType").val(),
				"deviceNameAbbr" : $("#ipt_deviceNameAbbr").val(),
				"os" : $("#ipt_os").val(),
				"deviceIcon" : $("#ipt_deviceIcon").val(),
                "moClassID" : $("#ipt_mobjectClassID").attr("alt"),
				"resCategoryID" : $("#ipt_resCategoryID").val(),
			},

			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
//					$.messager.alert("提示", "信息新增成功！", "info");
					$('#divAddEdit').dialog('close');
					reloadTable();
				} else {
					$.messager.alert("提示", "信息新增失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
	}
}

/**
 * 打开修改页面
 */
function toUpdate(id){
	openDescr();
	$("#ipt_id").val(id);
	initUpdateVal(id);
    $("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doUpdate(id);
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		window.location.href = window.location.href;
		$('#divAddEdit').dialog('close');
	});
	$('#divAddEdit').dialog('open');	
}

/**
 * 修改页面数据
 */
function doUpdate(id) {
	if (checkForm()) {
		//判断moClassId或者moClassNanme输入框里面有没有值
        var descr = $("#ipt_descr").val();
        var moId = $("#ipt_mobjectClassID").attr("alt");
        //如果都没有值显示提示MOClassID框
        if( !descr && !moId ) {
            closeDescr();
        }
        var path=getRootName();
        var uri=path+"/platform/snmpDevice/updateSnmpDevice";
		var ajax_param = {
            url: uri,
            type: "post",
            datdType: "json",
            data:{
                "id": id,
                "pen": $("#ipt_pen").val(),
                "deviceOID": $("#ipt_deviceOID").val(),
                "deviceModelName": $("#ipt_deviceModelName").val(),
                "deviceType": $("#ipt_deviceType").val(),
                "deviceNameAbbr": $("#ipt_deviceNameAbbr").val(),
                "os": $("#ipt_os").val(),
                "deviceIcon": $("#ipt_deviceIcon").val(),
                "resCategoryID": $("#ipt_resCategoryID").val(),
                "t": Math.random()

            },
            error: function () {
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function (data) {
                if (true == data || "true" == data) {
//					$.messager.alert("提示", "信息修改成功！", "info");
                    $('#divAddEdit').dialog('close');
                    reloadTable();
                } else {
                    $.messager.alert("提示", "信息修改失败！", "error");
                    reloadTable();
                }
            }
        }
        //如果moClassID出现更改
        if(moId != '') {
            ajax_param.data.moClassID =$("#ipt_mobjectClassID").attr("alt");
		}
        ajax_(ajax_param);
	}
}
	
/**
 * 刷新表格数据
 */
function reloadTable() {
	var deviceOID=$("#deviceOID").val();
	$('#tblDataList').datagrid('options').queryParams = {
		"deviceOID" : deviceOID
	};
	reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 验证表单信息
 * 
 */
function checkForm() {
	return checkInfo('#tblDetailInfo');
}
/**
 * 根据ID获取对象
 * 
 */
function initUpdateVal(id) {
	resetForm('tblDetailInfo');
	var path=getRootName();
		var uri=path+"/platform/snmpDevice/findSnmpDevice";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id" : id,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
		}
	}
	ajax_(ajax_param);
}

/**
 * 加载PEN维护信息列表页面
 */
//function loadRelatePen(){
//	var path=getRootName();
//	var uri=path+"/platform/snmpDevice/toSelectSnmpPenList";
//	window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
//}

/**
 * 加载产品类型信息列表页面
 */
//function loadRelateCate(){
//	var path=getRootName();
//	var uri=path+"/platform/snmpDevice/toSelectResCateList";
//	window.open(uri,"","height=480px,width=700px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
//}


/**
 * 批量删除
 */
function doBatchDel() {
    	var path = getRootName();
    	var checkedItems = $('[name=id]:checked');
    	var ids = null;
    	$.each(checkedItems, function(index, item) {
    		if (null == ids) {
    			ids = $(item).val();
    		} else {
    			ids += ',' + $(item).val();
    		}
    	});
    	if (null != ids) {
    		$.messager.confirm("提示", "确定删除所选中项？", function(r) {
    			if (r == true) {			
    				var uri =path+"/platform/snmpDevice/delBathSnmpDevice";
        			var ajax_param = {
        				url : uri,
        				type : "post",
        				datdType : "json",
        				data : {
        					"id":ids
        				},
    					success : function(data) {
    						if (true == data || "true" == data) {
//    							$.messager.alert("提示", "信息批量删除成功！", "info");
    							reloadTable();
    						} else {
    							$.messager.alert("提示", "信息批量删除失败！", "error");
    						}
    					}
    				};
    				ajax_(ajax_param);
    			}
    		});	
    	}else{
    		$.messager.alert('提示', '没有任何选中项', 'error');
    	}		
}


/**
 * 查看Div
 */
function toShowInfo(id){
	$('.input').val(""); 
	setRead(id);
	$("#btnClose2").unbind();
	$("#btnClose2").bind("click", function() {
		$('.input').val(""); 
		$('#divView').dialog('close');
	});
	$('#divView').dialog('open');
}

/**
 * 详情信息
 * @param id
 * @return
 */
function setRead(id){
	var path=getRootName();
	var uri=path+"/platform/snmpDevice/findSnmpDevice";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id" : id,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "lbl");
		}
	}
	ajax_(ajax_param);
}

/**
 * 打开PEN列表div
 */
function toAddPenlog() {
	resetForm('divPenFilter');
	doInitPenTable();	
	$('#divPenDialog').dialog('open');
}

/**
 * 加载PEN列表
 */
function doInitPenTable() {

	$('#tblPenList').datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 308,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		//fit : true,// 自动大小
		url : getRootName() + '/platform/snmpPen/listSnmpPen',
		remoteSort : false,
		idField : 'fldId',
		singleSelect : true,// 是否单选
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		columns : [ [ 
		{
			field : 'pen',
			title : 'PEN',
			width : 80,
			align : 'left',
			sortable : true
		},
		{
			field : 'enterpriseOID',
			title : '企业OID',
			width : 120,
			align : 'center',
			sortable : true
		},
		{
			field : 'organization',
			title : '企业名称',
			width : 150,
			align : 'center',									
			sortable : true
		},
		{
			field : 'contactTelephone',
			title : '联系电话',
			width : 80,
			align : 'center',
			sortable : true
		},
		{
			field : 'resManufacturerName',
			title : '关联厂商',
			width : 150,
			align : 'center',									
			sortable : true
		}
		] ]
	});
}

/**
 * PEN查询
 */
function reloadPenTable() {
	var organizationName=$("#organizationName").val();
	$('#tblPenList').datagrid('options').queryParams = {
		"organization" : organizationName
	};
	reloadTableCommon_1('tblPenList');
}

/**
 * 选择PEN
 */
function doAddPenlog() {
	var row = $('#tblPenList').datagrid('getSelected');
	if (row) {
		$('#divPenDialog').dialog('close');
		$("#ipt_pen").val(row.pen); //显示选中的数据
	}
}

/**
 * PEN取消选择
 */
function doPenCancel() {
	$('#divPenDialog').dialog('close');
}

//对应资源信息
function toAddCategorylog() {
	resetForm('divCategoryFilter');
	doInitCategoryTable();	
	$('#divCategoryDialog').dialog('open');
}

function doInitCategoryTable() {
	$('#tblCategoryList').datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 308,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		//fit : true,// 自动大小
		url : getRootName() + '/platform/resCateDefine/listResCateDef',
		remoteSort : false,
		idField : 'fldId',
		singleSelect : true,// 是否单选
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		columns : [ [ 
		             			{
									field : 'resCategoryName',
									title : '产品目录名称',
									width : 180,
									align : 'left'
								},
								{
									field : 'resCategoryAlias',
									title : '产品别名',
									width : 180,
									align : 'center'
								},
								{
									field : 'resModel',
									title : '设备型号',
									width : 100,
									align : 'center'
								},
								{
									field : 'resManufacturerName',
									title : '生产厂家',
									width : 100,
									align : 'center'
								}
		] ]
	});
}
function reloadCategoryTable() {
	var resCategoryName=$("#resCategoryName").val();
	$('#tblCategoryList').datagrid('options').queryParams = {
		"resCategoryName" : resCategoryName
	};
	reloadTableCommon_1('tblCategoryList');
}
function doAddCategorylog() {
	var row = $('#tblCategoryList').datagrid('getSelected');
	if (row) {
		$('#divCategoryDialog').dialog('close');
		$("#ipt_resCategoryID").val(row.resCategoryID); //显示选中的数据
		$("#ipt_resCategoryName").val(row.resCategoryName); //显示选中的数据
	}
}
function doCategoryCancel() {
	$('#divCategoryDialog').dialog('close');
}
/**
 *  选择对象类型
 * */
function choseMObjectTree() {
    var path = getRootPatch();
    var uri = path + "/monitor/addDevice/initTree";
    var ajax_param = {
        url : uri,
        type : "post",
        datdType : "json",
        data : {
            "trmnlBrandNm" : "",
            "qyType" : "brandName",
            "t" : Math.random()
        },
        error : function() {
            $.messager.alert("错误", "ajax_error", "error");
        },
        success : function(data) {
            dataTreeOrg = new dTree("dataTreeOrg", path
                + "/plugin/dTree/img/");
            dataTreeOrg.add(0, -1, "选择对象类型", "");

            // 得到树的json数据源
            var datas = eval('(' + data.menuLstJson + ')');
            // 遍历数据
            var gtmdlToolList = datas;
            for (var i = 0; i < gtmdlToolList.length; i++) {
                var _id = gtmdlToolList[i].classId;
                var _nameTemp = gtmdlToolList[i].classLable;
                var _parent = gtmdlToolList[i].parentClassId;
                var className = gtmdlToolList[i].className;
//					console.log("_id==="+_id+",_nameTemp==="+_nameTemp+",_parent==="+_parent);
                dataTreeOrg.add(_id, _parent, _nameTemp, "javascript:hiddenMObjectTreeSetValEasyUi('divMObject','ipt_mobjectClassID','"
                    + _id + "','"+ className + "','" + _nameTemp + "');");
            }
            $('#dataMObjectTreeDiv').empty();
            $('#dataMObjectTreeDiv').append(dataTreeOrg + "");
            $('#divMObject').dialog('open');
        }
    }
    ajax_(ajax_param);
}


/*
 * 选择隐藏树，对应展示需填数据
 */
function hiddenMObjectTreeSetValEasyUi(divId, controlId, showId,className, showVal) {
    //判断是否为叶子节点
    var path = getRootName();
    var uri=path+"/monitor/addDevice/isLeaf";
    var ajax_param={
        url:uri,
        type:"post",
        dateType:"json",
        data:{
            "classID" : showId,
            "t" : Math.random()
        },
        error : function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success:function(data){
            if (true == data || "true" == data) {
                $("#" + controlId).val(showVal);
                $("#" + controlId).attr("alt", showId);
                $("#" + divId).dialog('close');
                $("#ipt_descr").val(className);
                $('#deviceSetting').tabs('disableTab', 1);
                $('#deviceSetting').tabs('disableTab', 2);
                $('#deviceSetting').tabs('disableTab', 3);
			}
        }
    };
    ajax_(ajax_param);
    closeDescr();
}

/**
 * 设置对象类型
 * @return
 */
function setObjectType(){
    var showId = $("#ipt_mobjectClassID").attr("alt");
    var className = $("#ipt_descr").val();
    var result=checkInfo('#tblDetailInfo');
    if (result == true) {
        if(className ||showId== 15|| showId==16|| showId==54 || showId==81|| showId==86 || showId==19 || showId==20 || showId==53 || showId==44|| showId==122|| showId==134|| showId==131){
            var result=checkInfo('#tblDetailInfo');
        }
    }
}

function closeWindow(){
    $('#divMObject').dialog('close');
    closeDescr();
}

/**
 * 使文本框切换实现名称和id之间的显示（针对update功能）
 */
function  openDescr() {
    document.getElementById("ipt_mobjectClassID").style="visibility:hidden;position:absolute; z-index:1;";
    document.getElementById("ipt_descr").style="visibility:visible;position:absolute; z-index:1;";
    document.getElementById("ipt_mobjectClassID").validator=true;
    document.getElementById("ipt_descr").validator="{'default':'*','length':'1-64'}";
}
function  closeDescr() {
    document.getElementById("ipt_mobjectClassID").style="visibility:visible;position:absolute; z-index:1;";
    document.getElementById("ipt_descr").style="visibility:hidden;position:absolute; z-index:1;";
    document.getElementById("ipt_mobjectClassID").validator="{'default':'*','length':'1-64'}";
    document.getElementById("ipt_descr").validator=true;
}


