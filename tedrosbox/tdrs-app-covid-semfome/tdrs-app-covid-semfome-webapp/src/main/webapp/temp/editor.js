var selEl;
function tdrsSetHtml(){
	if(selEl){
		var html = selEl.outerHTML;
		var result = tidy_html5(html, options);
		tedros.setHtml(result);
	}
}
function tdrsSetClass(){
	if(selEl){
		tedros.setCssClass(selEl.className);
	}
}
function setAttribute(a,v){
	if(selEl){
		$(selEl).attr(a,v);
		tdrsSetHtml();
	}
	
}
function clear(){
	$('#spot').html('');
	removeSelectedArea();
	selEl = null;
}
function setElement(h){
	var $n = $(h);
	if(selEl){
		$n.replaceAll(selEl);
	}else{
		$('#spot').prepend($n);
	}
	selEl = $n.get(0);
	setTimeout(function(){
		addSelectedArea();
	}, 2000);
	buildTools();
	tedros.setElementTag(selEl.tagName);
	tdrsSetClass();
}
function addLast(h){
	var $n = $(h);
	if(selEl){
		$(selEl).append($n);
	}else
		$('#spot').append($n);
	selEl = $n.get(0);
	setTimeout(function(){
		addSelectedArea();
	}, 2000);
	buildTools();
	tedros.setElementTag(selEl.tagName);
	tdrsSetClass();
}
function addFirst(h){
	var $n = $(h);
	if(selEl){
		$(selEl).prepend($n);
	}else
		$('#spot').prepend($n);
	selEl = $n.get(0);
	setTimeout(function(){
		addSelectedArea();
	}, 2000);
	buildTools();
	tedros.setElementTag(selEl.tagName);
	tdrsSetClass();
}
function wrap(h){
	if(selEl){
		$(selEl).wrap(h);
		setParentEl();
	}
}
function remove(){
	if(selEl){
		$(selEl).remove();
		selEl = null;
		removeSelectedArea();
	}
}
function addClass(c){
	if(selEl){
		$(selEl).addClass(c);
		addSelectedArea();
		tdrsSetHtml();
	}
}
function removeClass(c){
	if(selEl){
		$(selEl).removeClass(c);
		addSelectedArea();
		tdrsSetHtml();
	}
}
function getSelTagName(){
	return selEl ? selEl.tagName : '';
}
function setParentEl(){
	var p = $(selEl).parent().get(0);
	if(p.id === 'spot')
		return;
	setSelEl(p);
}
function selectRoot(){
	var s = $('#spot').children();
	setSelEl(s.get(0));
}
function removeSel(){
	if(selEl){
	    $(selEl).off('click');	
	    selEl = null;
	}
	removeSelectedArea();
	$('#sel').html('');
	tedros.remElementTag();
}
function removeSelectedArea(){
	$('#selector-selected').css({
	    left: 0,
	    top:  0,
	    width: 0,
	    height: 0,
	    display: 'none'
	});
}
function addSelectedArea(){
	$target = $(selEl);
	targetOffset = $target[0].getBoundingClientRect(),
	targetHeight = targetOffset.height,
	targetWidth  = targetOffset.width;
	$('#selector-selected').css({
	    left:  (targetOffset.left - 4),
	    top:   (targetOffset.top - 4),
	    width: (targetWidth + 5),
	    height: (targetHeight + 8),
	    display: 'block'
	});
	elements.top.css({display:'none'});
	elements.left.css({display:'none'});
	elements.right.css({display:'none'});
	elements.bottom.css({display:'none'});
}
function buildTools(){
	var s = "<a id='selector-rem' class='sel' href='javascript: removeSel()' >[ - ]</a> ";
	s += "<a id='selector-parent'  class='sel' href='javascript: setParentEl()' >[ ^ ]</a> | ";
	//s += "<a id='selector-parent'  class='sel' href='javascript: setElement(\"<h1>teste</h1>\")' >[ set ]</a> | ";
	
	s += 'Element: '+selEl.tagName;
	if(selEl.id!='')
		s += ', id: '+selEl.id;
	if(selEl.className!='')
		s += ', class: '+selEl.className;
	if(selEl.tagName === 'IMG' && selEl.src!='')
		s += ', src: '+selEl.src;
	
	$('#sel').html(s);
}
function setSelEl(e) {
	selEl = e;
	buildTools();
	tdrsSetHtml();
	tdrsSetClass();
	tedros.setElementTag(selEl.tagName);
	addSelectedArea();
}

var elements = {
	    top: $('#selector-top'),
	    left: $('#selector-left'),
	    right: $('#selector-right'),
	    bottom: $('#selector-bottom')
	};

var lastTarget;
$(document).mousemove(function(event) {
	var el = event.target;
    if(el.id.indexOf('selector') !== -1 || el.tagName === 'BODY' 
    		|| el.tagName === 'HTML' || el.id === 'spot' || el.id === 'sel') return;
    
    elements.top.css({display:'block'});
	elements.left.css({display:'block'});
	elements.right.css({display:'block'});
	elements.bottom.css({display:'block'});
    
  	var $target = $(el);
    if(lastTarget && !(lastTarget===el))
    	$(lastTarget).off('click');
  	$target.off('click');
    $target.on('click', function(){
    	setSelEl(this);
    });
    lastTarget = el;
    targetOffset = $target[0].getBoundingClientRect(),
    	targetHeight = targetOffset.height,
    	targetWidth  = targetOffset.width;
    
    elements.top.css({
        left:  (targetOffset.left - 4),
        top:   (targetOffset.top - 4),
        width: (targetWidth + 5)
    });
    elements.bottom.css({
        top:   (targetOffset.top + targetHeight + 1),
        left:  (targetOffset.left  - 3),
        width: (targetWidth + 4)
    });
    elements.left.css({
        left:   (targetOffset.left  - 5),
        top:    (targetOffset.top  - 4),
        height: (targetHeight + 8)
    });
    elements.right.css({
        left:   (targetOffset.left + targetWidth + 1),
        top:    (targetOffset.top  - 4),
        height: (targetHeight + 8)
    });
    
});