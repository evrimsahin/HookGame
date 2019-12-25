import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;

class Sickle extends GraphicalContent implements HasEndPoint, UpdateEndPoint, CollisionData, Mortal, SetAlpha {
    public Vector2 size;
    public Vector2 direction;
    public Vector2 startPoint;
    public Vector2 endPoint;
    public Vector2 centerPoint;
    public boolean way;
    public boolean isShrinking;
    public Vector2 graspEnd;
    public Vector2 shrinkingStart;
    public double angOfArc;
    public Vector2 dirToStartPoint;
    public Vector2 realCenter;
    public float lifespan;
    int R, G, B;

    Arc arc;

    public Sickle(Vector2 startPoint, Vector2 direction, Vector2 size, boolean way) {
        this.way = way;
        R = 40;
        G = 40;
        B = 40;
        if (direction != null)
            this.direction = direction.getCopy();

        if (startPoint != null)
            this.startPoint = startPoint.getCopy();

        this.size = size;

        shrinkingStart = new Vector2(0, 0);
        isShrinking = false;

        arc = new Arc();
        updateCoordinates();
        updateGraphics();

    }


    @Override
    public void updateGraphics() {
        arc.setStrokeWidth(size.y);
        arc.setStroke(Color.rgb(R, G, B, lifespan));
        arc.setStrokeType(StrokeType.CENTERED);
        arc.setFill(null);
        arc.setStrokeLineCap(StrokeLineCap.BUTT);


    }


    @Override
    public void updateCoordinates() {
        endPoint = startPoint.getScaledVector(direction, size.x * 2);

        Vector2 temp = startPoint.getScaledVector(direction, size.x);
        Vector2 tempDir;

        Vector2 originCenter = new Vector2(temp.x, temp.y);

        if (isShrinking) {


            shrinkingStart.addScaledVector(direction, 2);
            originCenter.addScaledVector(direction, 2);


            Vector2 tempDir1;
            double mag = Math.sqrt(size.x * size.x - Vector2.substract(originCenter, graspEnd).squareMagnitude());

            if (!way)
                tempDir1 = direction.turn90Degree();
            else
                tempDir1 = direction.turnMinus90Degree();

            tempDir1.multiply((float) mag);
            tempDir1.add(Vector2.substract(graspEnd, originCenter));
            if (tempDir1 != null)
                shrinkingStart = tempDir1.getNormalized();
        } else {
            shrinkingStart = startPoint;
        }


        if (way)
            tempDir = direction.turn90Degree();
        else
            tempDir = direction.turnMinus90Degree();

        temp.addScaledVector(tempDir, size.y / 2);
        centerPoint = temp;

        Vector2 dirToStart;
        if (!isShrinking)
            dirToStart = Vector2.substract(shrinkingStart, originCenter).getNormalized();
        else
            dirToStart = shrinkingStart;

        if (dirToStart != null)
            dirToStartPoint = dirToStart.getCopy();

        if (dirToStart == null)
            return;

        realCenter = originCenter.getScaledVector(tempDir, size.y / 2);

        arc.setCenterX(centerPoint.x - direction.x * 0);
        arc.setCenterY(centerPoint.y - direction.y * 0);
        arc.setRadiusX(size.x);
        arc.setRadiusY(size.x);
        arc.setStartAngle(Vector2.calculateAngle(dirToStart));


//	if(way)
//		arc.setLength(-Math.abs(Vector2.calculateAngle(direction) - Vector2.calculateAngle(dirToStart)));
//		else
//		{
//			double ang = Math.abs(Vector2.calculateAngle(direction) - Vector2.calculateAngle(dirToStart));
//			if (ang > 180)
//			{
//				ang -=180;
//				ang = 180-ang;
//			}
//
//		arc.setLength(ang);
//
//		}
//
//	  angOfArc = arc.getLength();

        if (way) {
            double ang = Math.abs(Vector2.calculateAngle(direction) - Vector2.calculateAngle(dirToStart));

            if (ang > 180) {
                ang -= 180;
                ang = 180 - ang;

            }
            arc.setLength(-ang);

        } else {
            double ang = Math.abs(Vector2.calculateAngle(direction) - Vector2.calculateAngle(dirToStart));
            if (ang > 180) {
                ang -= 180;
                ang = 180 - ang;
            }

            arc.setLength(ang);

        }

        angOfArc = arc.getLength();

    }

    Pane temp123;

    @Override
    public void insertToPane(Pane pane) {
        if (!pane.getChildren().contains(arc))
            pane.getChildren().add(arc);

//temp123= pane;
//ArrayList<Vector2> a = new ArrayList<Vector2>();
//calculateCollisionData(a);
// a.clear();


    }


    @Override
    public Vector2 getEndPoint() {
        return endPoint;
    }


    @Override
    public void updateEndPoint(Vector2 endPoint) {
        if (!isShrinking && endPoint != null)
            this.startPoint = endPoint.getCopy();


    }


    @Override
    public void calculateCollisionData(ArrayList<Vector2> collisionPoints) {
        // TODO Auto-generated method stub
        Vector2 tempDir = dirToStartPoint.getCopy();


        for (int i = 0; i < Math.abs(angOfArc) / 3; i++) {
            Vector2 tempDirToStart = tempDir.getCopy();

            Vector2 radiusVector;
            if (way)
                radiusVector = Vector2.Rotate(tempDirToStart, +3 * i);
            else
                radiusVector = Vector2.Rotate(tempDirToStart, -3 * i);

            radiusVector.multiply(size.x - size.y / 2);


            for (int k = 0; k < size.y / 3; k++) {
                Vector2 tempCenter = realCenter.getCopy();
                radiusVector.multiply(1f);
                tempCenter.add(radiusVector);
                tempCenter.addScaledVector(radiusVector.getCopy().getNormalized(), k * 3);
                Vector2 temp = tempCenter.getCopy();
                collisionPoints.add(new Vector2((int) temp.x, (int) temp.y));
//			Circle circ = new Circle();
//			circ.setCenterX(temp.x);
//			circ.setCenterY(temp.y);
//			circ.setFill(Color.CRIMSON);
//
//			circ.setRadius(1);

//			if(temp123 != null && !temp123.getChildren().contains(circ) )
//				temp123.getChildren().add(circ);


            }

        }


    }


    @Override
    public boolean isDead() {
        if (arc.getLength() <= 30 && arc.getLength() >= -30) {
            arc.setStroke(null);
            return true;
        }
        return false;
    }


    @Override
    public void setAlpha(float alpha) {
        lifespan = alpha;
    }


}