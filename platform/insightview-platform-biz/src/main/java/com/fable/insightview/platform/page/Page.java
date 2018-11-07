package com.fable.insightview.platform.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
/**
 * 对分页的基本数据进行封装
 */ 
public class Page<T> {
 
	private int pageNo = 1;//页码，默认是第一页
    private int pageSize = 5;//每页显示的记录数，默认是15
    private int page = 1;//页码，默认是第一页
    private int rows = 5;//每页显示的记录数，默认是15
    private int totalRecord;//总记录数
    private int totalPage;//总页数
    private List<T> results;//对应的当前页记录
    private Map<String, Object> params = new HashMap<String, Object>();//其他的参数我们把它封装成一个Map对象
 
    public int getPageNo() {
       return pageNo;
    }
 
    public void setPageNo(int pageNo) {
       this.pageNo = pageNo;
    }
 
    public int getPageSize() {
       return pageSize;
    }
 
    public void setPageSize(int pageSize) {
       this.pageSize = pageSize;
    }
    
    public void setPage(int page) {
		this.page = page;
		this.pageNo = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
		this.pageSize = rows;
	}
	
	public int getPage() {
		return page;
	}

	public int getRows() {
		return rows;
	}

	public int getTotalRecord() {
       return totalRecord;
    }
 
    public void setTotalRecord(int totalRecord) {
       this.totalRecord = totalRecord;
     //��������ҳ���ʱ��������Ӧ����ҳ�����������Ŀ�����мӷ�ӵ�и�ߵ����ȼ������������Բ������š�
       int totalPage = totalRecord%pageSize==0 ? totalRecord/pageSize : totalRecord/pageSize + 1;
       this.setTotalPage(totalPage);
    }
 
    public int getTotalPage() {
       return totalPage;
    }
 
    public void setTotalPage(int totalPage) {
       this.totalPage = totalPage;
    }
 
    public List<T> getResults() {
       return results;
    }
 
    public void setResults(List<T> results) {
       this.results = results;
    }
   
    public Map<String, Object> getParams() {
       return params;
    }
   
    public void setParams(Map<String, Object> params) {
       this.params = params;
    }
 
    @Override
    public String toString() {
       StringBuilder builder = new StringBuilder();
       builder.append("Page [pageNo=").append(pageNo).append(", pageSize=")
              .append(pageSize).append(", results=").append(results).append(
                     ", totalPage=").append(totalPage).append(
                     ", totalRecord=").append(totalRecord).append("]");
       return builder.toString();
    }
 
}