/**
 * 
 */
package org.tedros.fx.builder;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;
import java.util.function.Function;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TJoin;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.server.query.TSelect;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TSelectQueryBuilder  {

	private TSelectQueryBuilder() {
		
	}
	
	public static TSelect build(TQuery ann, ITComponentDescriptor descriptor) {

		TSelect sel = create(ann);
		addJoins(ann, sel);
		addConditions(ann, sel, descriptor);
		addOrders(ann, sel);
		setAsc(ann, sel);
		
		return sel;
	}

	@SuppressWarnings("unchecked")
	public static TSelect create(TQuery ann) {
		TSelect sel = new TSelect(ann.entity());
		return sel;
	}

	public static void setAsc(TQuery ann, TSelect sel) {
		sel.setAsc(ann.asc());
	}

	public static void addOrders(TQuery ann, TSelect sel) {
		addOrders(ann, sel, o->true);
	}
	
	public static void addOrders(TQuery ann, TSelect sel, Function<TOrder,Boolean> validate) {
		for(TOrder o : ann.orderBy()) 
			if(validate.apply(o))
				sel.addOrderBy(o.alias(), o.field());
	}

	public static void addConditions(TQuery ann, TSelect sel) {
		addConditions(ann, sel, c->{
			Object value = getValue(c, null);
			addCondition(sel, c, value);
		});
	}
	
	public static void addConditions(TQuery ann, TSelect sel, ITComponentDescriptor descriptor) {
		addConditions(ann, sel, c->{
			Object value = getValue(c, descriptor);
			addCondition(sel, c, value);
		});
	}
	
	public static void addConditions(TQuery ann, TSelect sel, Consumer<TCondition> consume) {
		for(TCondition c : ann.condition()) 
			consume.accept(c);
	}
	
	public static void addCondition(TQuery ann, TSelect sel, Function<TCondition, Boolean> add) {
		addCondition(ann, sel, null, add);
	}
	
	public static void addCondition(TQuery ann, TSelect sel, ITComponentDescriptor descriptor, 
			Function<TCondition, Boolean> validate) {
		addCondition(ann, sel, validate, c->{
			Object value = getValue(c, descriptor);
			addCondition(sel, c, value);	
		});
	}
	
	public static void addCondition(TQuery ann, TSelect sel,
			Function<TCondition, Boolean> validate, Consumer<TCondition> consume) {
		for(TCondition c : ann.condition()) 
			if(validate.apply(c)) 
				consume.accept(c);
	}

	@SuppressWarnings("unchecked")
	public static void addCondition(TSelect sel, TCondition c, Object value) {
		sel.addCondition(c.logicOp(), c.alias(), c.field(), c.operator(), value, c.prompted());
	}

	public static Object getValue(TCondition c, ITComponentDescriptor descriptor) {
		Object value = !"".equals(c.value()) ? c.value() : null;
		if(c.valueBuilder()!=TGenericBuilder.class) {
			try {
				TGenericBuilder b = c.valueBuilder().getDeclaredConstructor().newInstance();
				b.setComponentDescriptor(descriptor);
				value = b.build();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
					InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e);
			}
		}
		return value;
	}

	public static void addJoins(TQuery ann, TSelect sel) {
		for(TJoin j : ann.join()) 
			sel.addJoin(j.type(), j.alias(), j.field(), j.joinAlias());
	}

}
