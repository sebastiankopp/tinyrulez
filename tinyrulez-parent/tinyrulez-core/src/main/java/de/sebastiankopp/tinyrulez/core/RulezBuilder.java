package de.sebastiankopp.tinyrulez.core;

import java.util.ArrayList;
import java.util.List;

public final class RulezBuilder {
	private RulezBuilder() {
		throw new UnsupportedOperationException();
	}
	
	public static <U> ComposedRuleBuilder<U> and(){
		return new AndBuilder<>(null);
	}
	
	public static <U> ComposedRuleBuilder<U> notAnd(){
		return new NotAndBuilder<>(null);
	}
	
	public static <U> ComposedRuleBuilder<U> or(){
		return new OrBuilder<>(null);
	}

	public static <U> ComposedRuleBuilder<U> notOr(){
		return new NotOrBuilder<>(null);
	}
	
	public static <U> Rule<U> not(Rule<U> negatedRule) {
		return new LogicalOperators.Not<>(negatedRule);
	}
	
	public static abstract class ComposedRuleBuilder<U> {
		protected final List<Rule<? super U>> rules = new ArrayList<>();
		private final ComposedRuleBuilder<U> parent;
		ComposedRuleBuilder(ComposedRuleBuilder<U> parent) {
			this.parent = parent;
		}
		
		public ComposedRuleBuilder<U> and() {
			return new AndBuilder<>(this);
		}
		
		public ComposedRuleBuilder<U> notAnd() {
			return new NotAndBuilder<>(this);
		}
		
		public ComposedRuleBuilder<U> or() {
			return new AndBuilder<>(this);
		}
		
		public ComposedRuleBuilder<U> notOr() {
			return new NotOrBuilder<>(this);
		}
		
		public ComposedRuleBuilder<U> rule(Rule<? super U> rule) {
			rules.add(rule);
			return this;
		}
		
		public ComposedRuleBuilder<U> not(Rule<? super U> negatedRule) {
			rules.add(new LogicalOperators.Not<>(negatedRule));
			return this;
		}
		
		public ComposedRuleBuilder<U> up() {
			return up(null);
		}
		public ComposedRuleBuilder<U> up(String msgOnMismatch) {
			if (parent == null) {
				return this;
			}
			parent.rules.add(buildMe(msgOnMismatch));
			return parent;
		}
		
		public Rule<U> build() {
			return build(null);
		}
		public Rule<U> build(String msgOnMismatch) {
			Rule<U> ownRule = buildMe(msgOnMismatch);
			if (parent != null) {
				parent.rules.add(ownRule);
				return parent.build();
			}
			return ownRule;
		}
		
		abstract Rule<U> buildMe(String msgOnMismatch);
	}
	
	static class AndBuilder<U> extends ComposedRuleBuilder<U> {
		AndBuilder(ComposedRuleBuilder<U> parent) {
			super(parent);
		}

		@Override
		Rule<U> buildMe(String msgOnMismatch) {
			return new LogicalOperators.And<>(rules, msgOnMismatch);
		}
	}
	
	static class NotAndBuilder<U> extends ComposedRuleBuilder<U> {
		NotAndBuilder(ComposedRuleBuilder<U> parent) {
			super(parent);
		}

		@Override
		Rule<U> buildMe(String msgOnMismatch) {
			return new LogicalOperators.Not<>(new LogicalOperators.And<>(rules), msgOnMismatch);
		}
	}
	
	static class OrBuilder<U> extends ComposedRuleBuilder<U> {
		OrBuilder(ComposedRuleBuilder<U> parent) {
			super(parent);
		}

		@Override
		Rule<U> buildMe(String msgOnMismatch) {
			return new LogicalOperators.Or<>(rules, msgOnMismatch);
		}
	}
	static class NotOrBuilder<U> extends ComposedRuleBuilder<U> {
		NotOrBuilder(ComposedRuleBuilder<U> parent) {
			super(parent);
		}

		@Override
		Rule<U> buildMe(String msgOnMismatch) {
			return new LogicalOperators.Not<>(new LogicalOperators.Or<>(rules), msgOnMismatch);
		}
	}
}
