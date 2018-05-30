  function onsearch(obj,path)
    {
    	with(document.forms[0])
    	{
    		action=path+"/e1001.html?ca001="+obj;
    		submit();
    	}
    }