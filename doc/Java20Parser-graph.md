# Graph representation of Java 20 Grammar


CompilationUnit
├── OrdinaryCompilationUnit
│   ├── PackageDeclaration
│   ├── ImportDeclaration[]
│   │   ├── SingleTypeImportDeclaration
│   │   ├── TypeImportOnDemandDeclaration
│   │   ├── SingleStaticImportDeclaration
│   │   └── StaticImportOnDemandDeclaration
│   └── TopLevelClassOrInterfaceDeclaration[]
└── ModularCompilationUnit
    ├── ImportDeclaration[]
    └── ModuleDeclaration

TypeDeclaration
├── ClassDeclaration
│   ├── NormalClassDeclaration
│   │   ├── ClassModifier[]
│   │   ├── TypeParameters?
│   │   ├── ClassExtends?
│   │   ├── ClassImplements?
│   │   ├── ClassPermits?
│   │   └── ClassBody
│   │       └── ClassBodyDeclaration[]
│   │           ├── ClassMemberDeclaration
│   │           │   ├── FieldDeclaration
│   │           │   ├── MethodDeclaration
│   │           │   ├── ClassDeclaration
│   │           │   └── InterfaceDeclaration
│   │           ├── InstanceInitializer
│   │           ├── StaticInitializer
│   │           └── ConstructorDeclaration
│   ├── EnumDeclaration
│   │   ├── ClassModifier[]
│   │   ├── ClassImplements?
│   │   └── EnumBody
│   │       ├── EnumConstantList?
│   │       └── EnumBodyDeclarations?
│   └── RecordDeclaration
│       ├── ClassModifier[]
│       ├── TypeParameters?
│       ├── RecordHeader
│       ├── ClassImplements?
│       └── RecordBody
└── InterfaceDeclaration
    ├── NormalInterfaceDeclaration
    │   ├── InterfaceModifier[]
    │   ├── TypeParameters?
    │   ├── InterfaceExtends?
    │   ├── InterfacePermits?
    │   └── InterfaceBody
    │       └── InterfaceMemberDeclaration[]
    │           ├── ConstantDeclaration
    │           ├── InterfaceMethodDeclaration
    │           ├── ClassDeclaration
    │           └── InterfaceDeclaration
    └── AnnotationInterfaceDeclaration
        ├── InterfaceModifier[]
        └── AnnotationInterfaceBody
            └── AnnotationInterfaceMemberDeclaration[]

Type
├── PrimitiveType
│   ├── NumericType
│   │   ├── IntegralType (byte, short, int, long, char)
│   │   └── FloatingPointType (float, double)
│   └── BooleanType
└── ReferenceType
    ├── ClassOrInterfaceType
    ├── TypeVariable
    └── ArrayType

Expression
├── PrimaryExpression
│   ├── Literal
│   ├── ClassLiteral
│   ├── This
│   ├── ParenthesizedExpression
│   └── ClassInstanceCreationExpression
├── PostfixExpression
│   ├── PostIncrementExpression
│   └── PostDecrementExpression
├── UnaryExpression
│   ├── PreIncrementExpression
│   ├── PreDecrementExpression
│   └── UnaryExpressionNotPlusMinus
├── BinaryExpression
│   ├── MultiplicativeExpression
│   ├── AdditiveExpression
│   ├── ShiftExpression
│   ├── RelationalExpression
│   ├── EqualityExpression
│   ├── BitwiseExpression
│   └── LogicalExpression
├── ConditionalExpression
├── AssignmentExpression
├── LambdaExpression
└── SwitchExpression

Statement
├── Block
├── EmptyStatement
├── LabeledStatement
├── ExpressionStatement
├── IfStatement
├── AssertStatement
├── SwitchStatement
├── WhileStatement
├── DoStatement
├── ForStatement
│   ├── BasicForStatement
│   └── EnhancedForStatement
├── BreakStatement
├── ContinueStatement
├── ReturnStatement
├── ThrowStatement
├── SynchronizedStatement
├── TryStatement
└── YieldStatement

Annotation
├── NormalAnnotation
├── MarkerAnnotation
└── SingleElementAnnotation

Modifier
├── ClassModifier
├── FieldModifier
├── MethodModifier
├── ConstructorModifier
├── InterfaceModifier
├── ConstantModifier
└── VariableModifier