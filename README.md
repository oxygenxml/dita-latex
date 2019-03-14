# dita-latex
DITA Open Toolkit plugin which allows publishing embedded Latex mathematical equations to HTML and PDF.

The plugin was tested and developed with DITA OT 3.2.1.

If you set the @outputclass="embed-latex" attribute on a DITA <foreign> element the plugin will attempt to convert it to SVG.
The "samples" folder contains a sample DITA topic with a Latex equation which is properly displayed when converted to HTML and PDF-based outputs.

The plugin contains a Java library provided by the JLatexMath under the GPL+CP license:
https://github.com/opencollab/jlatexmath/blob/master/LICENSE
