package business.services;

import java.util.Locale;

public class SVG {

    StringBuilder svg = new StringBuilder();

    private int x;
    private int y;
    private String viewBox;
    private int width;
    private int height;


    private final String headerTemplate = "<svg " +
            "height=\"%d%%\" " +
            "width=\"%d%%\" " +
            "viewBox=\"%s\" "+
            "x=\"%d\"   " +
            "y=\"%d\"   " +
            " preserveAspectRatio=\"xMinYMin\">";

    private final String rectTemplate = "<rect x=\"%d\" y=\"%d\" height=\"%.1f\" width=\"%.1f\" style=\"stroke:#000000; fill: #ffffff\" />";
    private final String lineTemplate = "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:#000000; fill: #ffffff\" />";
    private final String textTemplate = "<text x=\"%d\" y=\"%d\" style=\"text-anchor: middle; font-size: 0.7rem\">\"%.1f\"</text>";
    private final String textTemplateRotate = "<text style=\"text-anchor: middle; font-size: 0.7rem\" transform=\"translate(%d,%d) rotate(%d)\">\"%.1f\"</text>";
    private final String shedLineTemplate = "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:#000000; stroke-width:3; fill: #ffffff\" />";
    private final String dottedLineTemplate = "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:#000000; stroke-dasharray:5,5; fill: #ffffff\" />";

    public SVG(int x, int y, String viewBox, int width, int height)
    {
        Locale.setDefault(new Locale("US"));
        this.x = x;
        this.y = y;
        this.viewBox = viewBox;
        this.width = width;
        this.height = height;
        svg.append(String.format(headerTemplate, height, width, viewBox, x, y ));
    }

    public void addRect(double x, double y, double height, double width)
    {
        int x1 = (int) Math.round(x);
        int y1 = (int) Math.round(y);
        svg.append(String.format(rectTemplate, x1, y1, height, width));
    }

    public void addLine(int x1, int y1, int x2, int y2 )
    {
        svg.append(String.format(lineTemplate,x1,y1,x2,y2));
    }
    public void addShedLine(int x1, int y1, int x2, int y2 )
    {
        svg.append(String.format(shedLineTemplate,x1,y1,x2,y2));
    }

    public void addDottedLine(int x1, int y1, int x2, int y2 )
    {
        svg.append(String.format(dottedLineTemplate,x1,y1,x2,y2));
    }

    public void addText(int x, int y,double input){
        svg.append(String.format(textTemplate,x,y,input));
    }
    public void addTextRotate(int x, int y,int z, double input){
        svg.append(String.format(textTemplateRotate,x,y,z,input));
        System.out.println(String.format(textTemplateRotate,x,y,z,input));
    }


    public void addSvg(SVG innerSVG)
    {
        svg.append(innerSVG.toString());
    }

    @Override
    public String toString()
    {
        return svg.toString() + "</svg>" ;
    }
}


