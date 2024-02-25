package com.sini.mysns.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -876296618L;

    public static final QMember member = new QMember("member1");

    public final com.sini.mysns.domain.QBaseTimeEntity _super = new com.sini.mysns.domain.QBaseTimeEntity(this);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath memberName = createString("memberName");

    public final ListPath<com.sini.mysns.global.config.security.MemberRole, EnumPath<com.sini.mysns.global.config.security.MemberRole>> memberRoles = this.<com.sini.mysns.global.config.security.MemberRole, EnumPath<com.sini.mysns.global.config.security.MemberRole>>createList("memberRoles", com.sini.mysns.global.config.security.MemberRole.class, EnumPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerDate = _super.registerDate;

    public final StringPath url = createString("url");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

