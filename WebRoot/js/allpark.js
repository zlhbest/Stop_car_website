  function onEdit(obj,path)
    {
    	with(document.forms[0])
    	{
    		action=path+"/c1012.html?ca001="+obj;
    		submit();
    	}
    }
  function onDel(obj,path)
  {
 	 with(document.forms[0])
 	 {
 		 action=path+"/c1014.html?ca001="+obj;
 		 submit();
 	 }
  }
  //定义计数器,用以记录选中的checkbox的个数
  var count=0;
  function onSelect(obj)
  {
	   with(document.forms[0])
	   {
		   obj?count++:count--;
		   next[2].disabled=(count==0);
	   }
  }