import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITcr, defaultValue } from 'app/shared/model/tcr.model';

export const ACTION_TYPES = {
  FETCH_TCR_LIST: 'tcr/FETCH_TCR_LIST',
  FETCH_TCR: 'tcr/FETCH_TCR',
  CREATE_TCR: 'tcr/CREATE_TCR',
  UPDATE_TCR: 'tcr/UPDATE_TCR',
  DELETE_TCR: 'tcr/DELETE_TCR',
  RESET: 'tcr/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITcr>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TcrState = Readonly<typeof initialState>;

// Reducer

export default (state: TcrState = initialState, action): TcrState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TCR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TCR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TCR):
    case REQUEST(ACTION_TYPES.UPDATE_TCR):
    case REQUEST(ACTION_TYPES.DELETE_TCR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TCR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TCR):
    case FAILURE(ACTION_TYPES.CREATE_TCR):
    case FAILURE(ACTION_TYPES.UPDATE_TCR):
    case FAILURE(ACTION_TYPES.DELETE_TCR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TCR_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TCR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TCR):
    case SUCCESS(ACTION_TYPES.UPDATE_TCR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TCR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/tcrs';

// Actions

export const getEntities: ICrudGetAllAction<ITcr> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TCR_LIST,
  payload: axios.get<ITcr>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITcr> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TCR,
    payload: axios.get<ITcr>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITcr> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TCR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITcr> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TCR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITcr> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TCR,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
