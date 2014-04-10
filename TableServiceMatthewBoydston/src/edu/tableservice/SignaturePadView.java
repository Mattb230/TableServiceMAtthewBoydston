package edu.tableservice;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SignaturePadView extends View {
	
	private Paint paint = new Paint();
	private Path path = new Path();
	
	private float lastTouchX;
	private float lastTouchY;

	// A "Stroke" is a collection of points drawn by the user as she puts
	// her finger down, moves, and then lifts her finger up
	private static class Stroke {
		private ArrayList<Point> mPoints = new ArrayList<Point>();
	}
	
	// A list of all the Strokes the user has drawn with their finger
	private ArrayList<Stroke> mStrokes = new ArrayList<SignaturePadView.Stroke>();

	
	
	public SignaturePadView(Context context) {
		super(context);
	}
	
	public SignaturePadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
	}
	
	public SignaturePadView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	Stroke currentStroke = new Stroke();
	Point currentPoint = new Point();

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float eventX = event.getX();
		float eventY = event.getY();
		currentStroke.mPoints.clear();
		
		
		switch (event.getAction()){
		case MotionEvent.ACTION_DOWN:
			//path.moveTo(eventX, eventY);
			currentPoint.set((int) eventX, (int) eventY);
			currentStroke.mPoints.add(currentPoint);

			
            //lastTouchX = eventX;
            //lastTouchY = eventY;
            //return true;
            
		case  MotionEvent.ACTION_MOVE:
			
		case MotionEvent.ACTION_UP:
			//loops through all the historical points and adds them to the current stroke
			int historySize = event.getHistorySize();
			for(int i = 0; i < historySize; i++){
				float historicalX = event.getHistoricalX(i);
				float historicalY = event.getHistoricalY(i);
				//path.lineTo(historicalX, historicalY);	
				currentPoint.set((int) historicalX, (int) historicalY);
				currentStroke.mPoints.add(currentPoint);
				}
			//path.lineTo(eventX, eventY);
			break;
			
		default:
            return false;
        
		}//end switch
		//add the current stroke to the list of all the strokes 
		mStrokes.add(currentStroke);
		//redraw
		invalidate();
		//currentStroke.mPoints.clear();

        //lastTouchX = eventX;
        //lastTouchY = eventY;
        
		return true;
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO implement this
		//super.onDraw(canvas);
		canvas.drawColor(Color.LTGRAY);
		//int x = mStrokes.get(0).mPoints.get(0).x;
		//int y = mStrokes.get(0).mPoints.get(0).y;
		//path.moveTo(x, y);
		//canvas.drawPath(path, paint);
		//int x;
		//int y;
		
		
		  for(int i = 0; i < mStrokes.size(); i++){
		 
			if(mStrokes.get(i).mPoints.size() == 0)
				break;
			
			int x = mStrokes.get(i).mPoints.get(0).x;
			int y = mStrokes.get(i).mPoints.get(0).y;
			//path.moveTo(x, y);	

			for(int j = 0; j < mStrokes.get(i).mPoints.size(); j++){
				x = mStrokes.get(i).mPoints.get(j).x;
				y = mStrokes.get(i).mPoints.get(j).y;
				path.lineTo(x, y);

			}
			//invalidate();
			//path.close();
			//path.moveTo(x, y);	

			//canvas.drawPath(path, paint);
		}//end for
		
		
		canvas.drawPath(path, paint);
		

		
	}
	
	



}
