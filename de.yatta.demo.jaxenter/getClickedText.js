{
	var clickListener = function(evt) {
		java__callback__candidateClicked(evt.target.innerHTML);
	};
	var codeTags = document.getElementsByTagName('code');
	for(var i = 0; i < codeTags.length; i++) {
		codeTags[i].addEventListener('click', clickListener);
	}
}