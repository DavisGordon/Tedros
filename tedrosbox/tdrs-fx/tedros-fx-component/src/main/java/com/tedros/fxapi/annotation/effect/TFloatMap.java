/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/11/2014
 */
package com.tedros.fxapi.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A buffer that contains floating point data, intended for use as a parameter to effects such as DisplacementMap.
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE})
public @interface TFloatMap {
	
	
	/**
	 * <pre>
	 * Sets the sample for a specific band at the given (x,y) location.
	 * 
	 * Parameters:
	 * x - the x location
	 * y - the y location
	 * band - the band to set (must be 0, 1, 2, or 3)
	 * s - the sample value to set
	 * </pre>
	 * */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(value={ElementType.ANNOTATION_TYPE})
	public @interface TSample {
		/**
		 * the x location
		 */
		public int x();
		/**
		 * the y location
		 */
		public int y();
		/**
		 * the band to set (must be 0, 1, 2, or 3)
		 */
		public int band();
		/**
		 * the sample value to set
		 * */
		public float s();
	}
	
	/**
	 * <pre><ul><li>If x, y and s0 is given, then sets the sample for the first band at the given (x,y) location.</li>
	 * <li>If x, y, s0 and s1 is given, then sets the sample for the first two bands at the given (x,y) location.</li>
	 * <li>If x, y, s0, s1 and s2 is given, then sets the sample for the first three bands at the given (x,y) location.</li>
	 * <li>If x, y, s0, s1, s2 and s3 is given, then sets the sample for each of the four bands at the given (x,y) location.</li>
	 * </ul>
	 * Parameters:
	 * 	x - the x location
	 * 	y - the y location
	 * 	s0 - the sample value to set for the first band
	 * 	s1 - the sample value to set for the second band
	 * 	s2 - the sample value to set for the third band
	 * 	s3 - the sample value to set for the fourth band
	 * </pre>
	 * */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(value={ElementType.ANNOTATION_TYPE})
	public @interface TSamples {
		/**
		 * the x location
		 */
		public int x();
		/**
		 * the y location
		 */
		public int y();
		/**
		 * the sample value to set for the first band
		 */
		public float s0();
		
		/**
		 * the sample value to set for the first band
		 */
		public float s1() default -99229922;
		
		/**
		 * the sample value to set for the first band
		 */
		public float s2() default -99229922;
		
		/**
		 * the sample value to set for the first band
		 */
		public float s3() default -99229922;
		
	}
	
	/**
	 * Sets the sample for a specific band at the given (x,y) location.
	 * */
	public TSample[] sample() default {@TSample(x=-99229922, y=-99229922, band=-99229922, s=-99229922)};
	
	
	/**
	 * Sets the sample for a choice band at the given (x,y) location.
	 * */
	public TSamples[] samples() default {@TSamples(x=-99229922, y=-99229922, s0=-99229922)};
	
	
	/**
	 * <pre>
	 * Sets the value of the property height.
	 * 
	 * Property description:
	 * The height of the map, in pixels.
	 *        Min:    1
	 *        Max: 4096
	 *    Default:    1
	 *   Identity:  n/a
	 *   
	 *   Default value: 1
	 * </pre>
	 * */
	public int height() default 1;
	
	/**
	 * <pre>
	 * Sets the value of the property width.
	 * 
	 * Property description:
	 * The width of the map, in pixels.
	 *        Min:    1
	 *        Max: 4096
	 *    Default:    1
	 *   Identity:  n/a
	 *   
	 *   Default value: 1
	 * </pre>
	 * */
	public int width() default 1;
	
	
}
