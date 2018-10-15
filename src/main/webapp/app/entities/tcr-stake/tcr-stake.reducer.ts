import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITcrStake, defaultValue } from 'app/shared/model/tcr-stake.model';

export const ACTION_TYPES = {
  FETCH_TCRSTAKE_LIST: 'tcrStake/FETCH_TCRSTAKE_LIST',
  FETCH_TCRSTAKE: 'tcrStake/FETCH_TCRSTAKE',
  CREATE_TCRSTAKE: 'tcrStake/CREATE_TCRSTAKE',
  UPDATE_TCRSTAKE: 'tcrStake/UPDATE_TCRSTAKE',
  DELETE_TCRSTAKE: 'tcrStake/DELETE_TCRSTAKE',
  RESET: 'tcrStake/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITcrStake>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TcrStakeState = Readonly<typeof initialState>;

// Reducer

export default (state: TcrStakeState = initialState, action): TcrStakeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TCRSTAKE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TCRSTAKE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TCRSTAKE):
    case REQUEST(ACTION_TYPES.UPDATE_TCRSTAKE):
    case REQUEST(ACTION_TYPES.DELETE_TCRSTAKE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TCRSTAKE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TCRSTAKE):
    case FAILURE(ACTION_TYPES.CREATE_TCRSTAKE):
    case FAILURE(ACTION_TYPES.UPDATE_TCRSTAKE):
    case FAILURE(ACTION_TYPES.DELETE_TCRSTAKE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TCRSTAKE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TCRSTAKE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TCRSTAKE):
    case SUCCESS(ACTION_TYPES.UPDATE_TCRSTAKE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TCRSTAKE):
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

const apiUrl = 'api/tcr-stakes';

// Actions

export const getEntities: ICrudGetAllAction<ITcrStake> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TCRSTAKE_LIST,
  payload: axios.get<ITcrStake>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITcrStake> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TCRSTAKE,
    payload: axios.get<ITcrStake>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITcrStake> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TCRSTAKE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITcrStake> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TCRSTAKE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITcrStake> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TCRSTAKE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
