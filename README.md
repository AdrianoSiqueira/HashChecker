<h1>Hash Checker</h1>

<h5>About</h5>
<p>This program calculates the hash encryption of a file and verifies it's integrity comparing with the official hash contained in another file.</p>
<p>Inside the <b>build</b> folder is the executable "HashChecker.jar", with which you can execute the program without the need to compile the source code.</p>

<h5>How to Run</h5>
<p><i>Windows:</i> Double click in HashChecker.jar file.</p>
<p><i>Linux and Mac:</i> Double click in HashChecker.jar file. If its not work, open a terminal window and type 'java -jar HashChecker.jar'</p>

<h5>Requirements</h5>
<ul>
    <li><a href="https://www.oracle.com/java/technologies/javase-jre8-downloads.html">Oracle Java 8</a></li>
</ul>

<h5>Supported Encryptions</h5>
<table>
  <tr>
    <td><ul><li><a href="https://en.wikipedia.org/wiki/MD5">MD5</a></li></ul></td>
    <td><ul><li><a href="https://en.wikipedia.org/wiki/SHA-1">SHA-1</a></li></ul></td>
    <td><ul><li><a href="https://en.wikipedia.org/wiki/SHA-2">SHA-224</a></li></ul></td>
  </tr>
  <tr>
    <td><ul><li><a href="https://en.wikipedia.org/wiki/SHA-2">SHA-256</a></li></ul></td>
    <td><ul><li><a href="https://en.wikipedia.org/wiki/SHA-2">SHA-384</a></li></ul></td>
    <td><ul><li><a href="https://en.wikipedia.org/wiki/SHA-2">SHA-512</a></li></ul></td>
  </tr>
</table>

<h5>Known Issues</h5>
<ol>
    <li>If the official file is not a valid text file, the program will be behave unpredictably.</li>
</ol>

<h5>Change log v3.3:</h5>
<ul>
    <li>Code ported to run with Java 8.</li>
</ul>

<h5>Change log v3.2:</h5>
<ul>
    <li>Added color style to table report.</li>
    <li>Added an integrity bar to show the status.</li>
    <li>Added tooltips in components.</li>
    <li>Project files reorganization.</li>
</ul>

<h5>Change log v3.1:</h5>
<ul>
    <li>Added about and manual.</li>
    <li>Added CSS to style.</li>
    <li>Added custom fonts.</li>
    <li>Added hints in components.</li>
    <li>Fix hash parse from bytes to string.</li>
</ul>

<h5>Change log v3.0:</h5>
<ul>
    <li>New GUI.</li>
    <li>Added multi platform support.</li>
    <li>Added multi thread support for better performance.</li>
    <li>Added support to other languages.</li>
    <li>Now it checks all the official hashes to better determine integrity.</li>
    <li>The results are now shown in a table.</li>
    <li>The process starts automatically as soon as the two files are opened.</li>
    <li>Only valid hashes are checked.</li>
</ul>

<h5>Change log v2.1:</h5>
<ul>
    <li>Added extension filter when opening the official file to prevent opening an invalid file.</li>
    <li>Updated library's classes and functions.</li>
</ul>

<h5>Change log v2.0:</h5>
<ul>
    <li>Added automatic encryption detection as soon as the official file is opened, and if the file has multiple hashes, the best will be selected.</li>
    <li>Added automatic exit if it running in an unsupported operating system.</li>
    <li>Added icons in buttons.</li>
    <li>Added more consistent error promptings.</li>
    <li>Removed the limitation of single hash in official file.</li>
    <li>Some improvements in performance.</li>
    <li>Translation of all project to english.</li>
</ul>
