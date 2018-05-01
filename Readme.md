<h1>Introduction</h1>
<p>Java implementation to list all possible Levenshtein (i.e., edit) sequences from one inserts/deletes/replaces.</p>

<h4>Input Example:</h4>
<ul>
	<li>paris</li>
	<li>alice</li>
</ul>

<h4>Output Example:</h4>
<p>There are total of two sequences:</p>
<ol>
	<li>paris delete p --> aris replace r by l --> alis replace s by c --> alic insert e --> alice</li>
	<li>paris delete p --> aris replace r by l --> alis insert c --> alics replace s by e --> alice</li>
</ol>