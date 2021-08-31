package me.txb1.render.window;

import eu.firedata.system.controller.Controller;
import eu.firedata.system.controller.annotations.Priority;
import eu.firedata.system.controller.annotations.method.Construct;
import eu.firedata.system.controller.annotations.type.Component;
import eu.firedata.system.controller.annotations.variable.Fill;
import lombok.Getter;
import me.txb1.render.Renderer;

import javax.swing.*;
import java.awt.*;

/**
 * @author Txb1 at 30.08.2021
 * @project NetSimulation
 */

@Getter
@Component
public class Window extends JFrame {

    @Fill
    private Renderer render;

    private RenderPanel renderPanel;

    @Construct(priority = Priority.HIGH)
    public void handleConstruct() {
        this.setTitle("NetSimulation");


        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.renderPanel = new RenderPanel();
        this.renderPanel.setFocusable(true);
        this.renderPanel.requestFocusInWindow();

        this.add(this.renderPanel);
        this.pack();

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int width = (int) env.getMaximumWindowBounds().getSize().getWidth();
        int height = (int) env.getMaximumWindowBounds().getSize().getHeight();
        this.move(width / 2 - 500, height / 2 - 500);
        this.setSize(new Dimension(1000, 1000));

        this.setVisible(true);
    }

    public void startRenderThread(int milis) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(milis);
                    this.renderPanel.repaint();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }).start();
    }

    public class RenderPanel extends PanelDoubleBuffered {

        private final Window window = Controller.getContext().getComponent(Window.class);

        @Override
        public void paintBuffer(Graphics g) {
            super.paintBuffer(g);
            final Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            this.window.getRender().handleRender(g2d);
        }
    }

    public class PanelDoubleBuffered extends Panel {

        private int panelWidth;
        private int panelHeight;
        private Image offscreenImage;
        private Graphics offscreenGraphics;

        public PanelDoubleBuffered() {
            super();
        }

        @Override
        public void update(final Graphics g) {
            paint(g);
        }

        @Override
        public void paint(final Graphics g) {
            super.paint(g);
            // checks the buffersize with the current panelsize
            // or initialises the image with the first paint
            if (panelWidth != getSize().width
                    || panelHeight != getSize().height
                    || offscreenImage == null || offscreenGraphics == null) {
                resetBuffer();
            }
            if (offscreenGraphics != null) {
                //this clears the offscreen image, not the onscreen one
                offscreenGraphics.clearRect(0, 0, panelWidth, panelHeight);
                //calls the paintbuffer method with
                //the offscreen graphics as a param
                paintBuffer(offscreenGraphics);
                //we finaly paint the offscreen image onto the onscreen image
                g.drawImage(offscreenImage, 0, 0, this);
            }
        }

        private void resetBuffer() {
            // always keep track of the image size
            panelWidth = getSize().width;
            panelHeight = getSize().height;
            // clean up the previous image
            if (offscreenGraphics != null) {
                offscreenGraphics.dispose();
            }
            if (offscreenImage != null) {
                offscreenImage.flush();
            }
            // create the new image with the size of the panel
            offscreenImage = createImage(panelWidth, panelHeight);
            offscreenGraphics = offscreenImage.getGraphics();
        }

        public void paintBuffer(final Graphics g) {
            // in classes extended from this one, add something to paint here!
            // always remember, g is the offscreen graphics
        }
    }
}