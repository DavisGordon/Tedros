var selEl;
function tdrsSetHtml(){
	if(selEl){
		//var p = $(selEl).parent().get(0);
		var html = selEl.outerHTML;
		var result = tidy_html5(html, options);
		tedros.setHtml(result);
	}
	
}
function clear(){
	$('#spot').empty();
	removeSelectedArea();
	selEl = null;
	lastTarget = null;
}
function setElement(h){
	console.log(selEl.outerHtml);
	if(selEl){
		$(h).replaceAll(selEl);
		console.log(selEl.outerHtml);
		addSelectedArea();
	}else
		$('#spot').html(h);
}
function addLast(h){
	if(selEl){
		$(selEl).append(h);
		addSelectedArea();
	}else
		$('#spot').append(h);
}
function addFirst(h){
	if(selEl){
		$(selEl).prepend(h);
		addSelectedArea();
	}else
		$('#spot').prepend(h);
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
	lastTarget = selEl;
	setSelEl(p);
}
function removeSel(){
	selEl = null;
	if(lastTarget){
	    $(lastTarget).off('click');	
	    lastTarget = null;
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
function setSelEl(e) {
	selEl = e;
	
	var s = "<a id='selector-rem' class='sel' href='javascript: removeSel()' >[ - ]</a> ";
	s += "<a id='selector-parent'  class='sel' href='javascript: setParentEl()' >[ ^ ]</a> | ";
	s += 'Element: '+selEl.tagName;
	if(selEl.id!='')
		s += ', id: '+selEl.id;
	if(selEl.className!='')
		s += ', class: '+selEl.className;
	if(selEl.tagName === 'IMG' && selEl.src!='')
		s += ', src: '+selEl.src;
	
	$('#sel').html(s);
	tdrsSetHtml();
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
    
    if(lastTarget && !(lastTarget===el)){
    	$(lastTarget).off('click');	
    }
    
    $target.on('click', function(){
    	setSelEl(el);
    });
    lastTarget = el;
    targetOffset = $target[0].getBoundingClientRect(),
    	targetHeight = targetOffset.height,
    	targetWidth  = targetOffset.width;
    //console.log(targetOffset);
    
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