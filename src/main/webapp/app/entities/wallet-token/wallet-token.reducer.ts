import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWalletToken, defaultValue } from 'app/shared/model/wallet-token.model';

export const ACTION_TYPES = {
  FETCH_WALLETTOKEN_LIST: 'walletToken/FETCH_WALLETTOKEN_LIST',
  FETCH_WALLETTOKEN: 'walletToken/FETCH_WALLETTOKEN',
  CREATE_WALLETTOKEN: 'walletToken/CREATE_WALLETTOKEN',
  UPDATE_WALLETTOKEN: 'walletToken/UPDATE_WALLETTOKEN',
  DELETE_WALLETTOKEN: 'walletToken/DELETE_WALLETTOKEN',
  RESET: 'walletToken/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWalletToken>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type WalletTokenState = Readonly<typeof initialState>;

// Reducer

export default (state: WalletTokenState = initialState, action): WalletTokenState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WALLETTOKEN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WALLETTOKEN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_WALLETTOKEN):
    case REQUEST(ACTION_TYPES.UPDATE_WALLETTOKEN):
    case REQUEST(ACTION_TYPES.DELETE_WALLETTOKEN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_WALLETTOKEN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WALLETTOKEN):
    case FAILURE(ACTION_TYPES.CREATE_WALLETTOKEN):
    case FAILURE(ACTION_TYPES.UPDATE_WALLETTOKEN):
    case FAILURE(ACTION_TYPES.DELETE_WALLETTOKEN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLETTOKEN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WALLETTOKEN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_WALLETTOKEN):
    case SUCCESS(ACTION_TYPES.UPDATE_WALLETTOKEN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_WALLETTOKEN):
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

const apiUrl = 'api/wallet-tokens';

// Actions

export const getEntities: ICrudGetAllAction<IWalletToken> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_WALLETTOKEN_LIST,
  payload: axios.get<IWalletToken>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IWalletToken> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WALLETTOKEN,
    payload: axios.get<IWalletToken>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IWalletToken> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WALLETTOKEN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWalletToken> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WALLETTOKEN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWalletToken> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WALLETTOKEN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
