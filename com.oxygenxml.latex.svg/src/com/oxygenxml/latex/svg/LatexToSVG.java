package com.oxygenxml.latex.svg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.JLabel;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.scilab.forge.jlatexmath.DefaultTeXFont;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.scilab.forge.jlatexmath.cyrillic.CyrillicRegistration;
import org.scilab.forge.jlatexmath.greek.GreekRegistration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.lib.ExtensionFunctionCall;
import net.sf.saxon.lib.ExtensionFunctionDefinition;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.SequenceType;
import net.sf.saxon.value.StringValue;

public class LatexToSVG extends ExtensionFunctionDefinition {

	  @Override
	  public SequenceType[] getArgumentTypes() {
	    return new SequenceType[] { SequenceType.SINGLE_STRING};
	  }

	  @Override
	  public StructuredQName getFunctionQName() {
	    return new StructuredQName("LatexToSVG", "java:com.oxygenxml.latex.svg.LatexToSVG", "convert");
	  }

	  @Override
	  public SequenceType getResultType(SequenceType[] suppliedArgumentTypes) {
	    return SequenceType.SINGLE_STRING;
	  }

	@Override
	public ExtensionFunctionCall makeCallExpression() {
		return new ExtensionFunctionCall() {

			@Override
			public Sequence call(XPathContext arg0, Sequence[] arguments) throws XPathException {
				String latex = ((StringValue) arguments[0].iterate().next()).getStringValue();
				DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
				String svgNS = "http://www.w3.org/2000/svg";
				Document document = domImpl.createDocument(svgNS, "svg", null);
				SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(document);

				SVGGraphics2D g2 = new SVGGraphics2D(ctx, true);

				DefaultTeXFont.registerAlphabet(new CyrillicRegistration());
				DefaultTeXFont.registerAlphabet(new GreekRegistration());

				TeXFormula formula = new TeXFormula(latex);
				TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
				icon.setInsets(new Insets(5, 5, 5, 5));
				g2.setSVGCanvasSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
				g2.setColor(Color.white);
				g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());

				JLabel jl = new JLabel();
				jl.setForeground(new Color(0, 0, 0));
				icon.paintIcon(jl, g2, 0, 0);

				boolean useCSS = true;
				Writer writer = new StringWriter();
				try {
					g2.stream(writer, useCSS);
				} catch (SVGGraphics2DIOException e) {
					throw new XPathException(e);
				} finally {
					try {
						writer.close();
					} catch (IOException e) {
						throw new XPathException(e);
					}
				}
				return StringValue.makeStringValue(writer.toString());
			}
		};
	}

}
