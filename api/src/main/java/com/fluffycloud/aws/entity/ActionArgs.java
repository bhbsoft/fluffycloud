package com.fluffycloud.aws.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fluffycloud.aws.constants.Action;

public class ActionArgs
{

	private Action action;
	private List<Filter> filters;
	private Map<String, String> paramsToUpdate;

	private ActionArgs(ActionArgsBuilder args)
	{
		this.action = args.action;
		this.filters = args.filters;
		this.paramsToUpdate = args.paramsToUpdate;
	}

	public static class ActionArgsBuilder
	{
		private Action action;

		private List<Filter> filters;
		private Map<String, String> paramsToUpdate;

		public ActionArgsBuilder(Action action)
		{
			super();
			this.action = action;
		}

		public ActionArgsBuilder setFilters(List<Filter> filters)
		{
			this.filters = filters;
			return this;
		}

		public ActionArgsBuilder setParamsToUpdate(Map<String, String> paramsToUpdate)
		{
			this.paramsToUpdate = paramsToUpdate;
			return this;
		}

		public ActionArgs build()
		{
			return new ActionArgs(this);
		}

	}

	public Action getAction()
	{
		return action;
	}

	public List<Filter> getFilters()
	{
		return filters;
	}

	public Map<String, String> getParamsToUpdate()
	{
		if (null == paramsToUpdate)
		{
			return new HashMap<String, String>();
		}
		return paramsToUpdate;
	}

}
