INSERT INTO
  organization (id, code          , name         , super_id)
        VALUES (1 , 'ORG_CODE_01' , 'ORG NAME 01', null    )
             , (2 , 'ORG_CODE_02' , 'ORG NAME 02', 1       )
             , (3 , 'ORG_CODE_03' , 'ORG NAME 03', 1       )
             , (4 , 'ORG_CODE_04' , 'ORG NAME 04', 1       )
             , (5 , 'ORG_CODE_05' , 'ORG NAME 05', 1       )
             , (6 , 'ORG_CODE_06' , 'ORG NAME 06', 2       )
             , (7 , 'ORG_CODE_07' , 'ORG NAME 07', 3       )
             , (8 , 'ORG_CODE_08' , 'ORG NAME 08', 4       )
             , (9 , 'ORG_CODE_09' , 'ORG NAME 09', 5       )
             , (10, 'ORG_CODE_10' , 'ORG NAME 10', 6       )
             ;
