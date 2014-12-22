package jfxtras.internal.scene.control.skin.agenda;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import jfxtras.scene.control.agenda.Agenda;

/**
 * This class is not a class but a data holder, a record, all fields are accessed directly.
 * Its methods are utility methods, which normally would be statics in a util class. 
 */
public class LayoutHelp {
	public LayoutHelp(Agenda skinnable, AgendaWeekSkin agendaWeekSkin) {
		this.skinnable = skinnable;
		this.skin = agendaWeekSkin;
		dragPane = new DragPane(this);
	}
	final Agenda skinnable;
	final AgendaWeekSkin skin;
	final DragPane dragPane;
	
	final IntegerProperty highestNumberOfWholedayAppointmentsProperty = new SimpleIntegerProperty(0);
	
	final DoubleProperty paddingProperty = new SimpleDoubleProperty(3);
	final DoubleProperty timeColumnWhitespaceProperty = new SimpleDoubleProperty(10);
	final DoubleProperty wholedayAppointmentFlagpoleWidthProperty = new SimpleDoubleProperty(5);
	final DoubleProperty textHeightProperty = new SimpleDoubleProperty(0);
	final DoubleProperty titleDateTimeHeightProperty = new SimpleDoubleProperty(0);
	final DoubleProperty headerHeightProperty = new SimpleDoubleProperty(0);
	final DoubleProperty appointmentHeaderPaneHeightProperty = new SimpleDoubleProperty(0);
	final DoubleProperty timeWidthProperty = new SimpleDoubleProperty(0); 
	final DoubleProperty dayFirstColumnXProperty = new SimpleDoubleProperty(0); 
	final DoubleProperty dayWidthProperty = new SimpleDoubleProperty(0); 
	final DoubleProperty dayContentWidthProperty = new SimpleDoubleProperty(0); 
	final DoubleProperty dayHeightProperty = new SimpleDoubleProperty(0);  
	final DoubleProperty durationInMSPerPixelProperty = new SimpleDoubleProperty(0);
	final DoubleProperty hourHeighProperty = new SimpleDoubleProperty(0);
	
	SimpleDateFormat dayOfWeekDateFormat = new SimpleDateFormat("E", Locale.getDefault());
	DateTimeFormatter dayOfWeekDateTimeFormatter = new DateTimeFormatterBuilder().appendPattern("E").toFormatter(Locale.getDefault());
	SimpleDateFormat dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT, Locale.getDefault());
	DateTimeFormatter dateDateTimeFormatter = new DateTimeFormatterBuilder().appendLocalized(FormatStyle.SHORT, null).toFormatter(Locale.getDefault());
	final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	DateTimeFormatter timeDateTimeFormatter = new DateTimeFormatterBuilder().appendPattern("HH:mm").toFormatter(Locale.getDefault());
	
	/**
	 * 
	 * @param text
	 * @param width
	 * @param height
	 */
	public void clip(Text text, ObservableValue<? extends Number> width, ObservableValue<? extends Number> height) {
		Rectangle lClip = new Rectangle(0,0,0,0);
		lClip.widthProperty().bind(width);
		lClip.heightProperty().bind(height);
		text.setClip(lClip);
	}

	/**
	 * 
	 * @param node
	 */
	public void setupMouseOverAsBusy(final Node node) {
		// play with the mouse pointer to show something can be done here
		node.setOnMouseEntered( (mouseEvent) -> {
			if (!mouseEvent.isPrimaryButtonDown()) {						
				node.setCursor(Cursor.HAND);
				mouseEvent.consume();
			}
		});
		node.setOnMouseExited( (mouseEvent) -> {
			if (!mouseEvent.isPrimaryButtonDown()) {
				node.setCursor(Cursor.DEFAULT);
				mouseEvent.consume();
			}
		});
	}

	/**
	 * 
	 * @param localDateTime
	 * @param minutes
	 * @return
	 */
	public LocalDateTime roundTimeToNearestMinutes(LocalDateTime localDateTime, int minutes)
	{
		localDateTime = localDateTime.withSecond(0).withNano(0);
		int lMinutes = localDateTime.getMinute() % minutes;
		if (lMinutes < (minutes/2)) {
			localDateTime = localDateTime.plusMinutes(-1 * lMinutes);
		}
		else {
			localDateTime = localDateTime.plusMinutes(minutes - lMinutes);
		}
		return localDateTime;
	}
}